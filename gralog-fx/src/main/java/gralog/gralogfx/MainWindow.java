/* This file is part of Gralog, Copyright (c) 2016-2017 LaS group, TU Berlin.
 * License: https://www.gnu.org/licenses/gpl.html GPL version 3 or later. */
package gralog.gralogfx;

import gralog.plugins.*;
import gralog.structure.*;
import gralog.importfilter.*;
import gralog.exportfilter.*;
import gralog.generator.*;
import gralog.algorithm.*;

import gralog.gralogfx.events.RedrawOnProgress;
import gralog.gralogfx.views.ViewManager;
import gralog.preferences.Preferences;

import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.Alert.AlertType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The gralog main window.
 */
public class MainWindow extends Application {

    Stage stage;
    BorderPane root;
    MainMenu menu;
    VBox topPane;
    Tabs tabs;

    ModeButtons modeButtons;

    StatusBar statusBar;

    public MainWindow() {
        MainMenu.Handlers handlers = new MainMenu.Handlers();
        handlers.onNew = this::onNew;
        handlers.onGenerate = this::onGenerate;
        handlers.onOpen = this::onOpen;
        handlers.onSave = this::onSave;
        handlers.onDirectInput = this::onDirectInput;
        handlers.onLoadPlugin = this::onLoadPlugin;
        handlers.onExit = () -> stage.close();
        handlers.onRunAlgorithm = this::onRunAlgorithm;

        handlers.onAboutGralog = () -> (new AboutStage(this)).showAndWait();
        handlers.onAboutGraph = () -> {
            Structure structure = getCurrentStructure();
            if (structure == null)
                return;
            String url = structure.getDescription().url();
            if (url != null && !url.trim().equals(""))
                this.getHostServices().showDocument(url);
        };

        statusBar = new StatusBar();

        menu = new MainMenu(handlers);
        modeButtons = new ModeButtons(statusBar::setStatus);

        topPane = new VBox();
        topPane.getChildren().addAll(menu.getMenuBar(), modeButtons.getButtonBar());

        tabs = new Tabs(this::currentStructureChanged);

        root = new BorderPane();
        //root.setFocusTraversable(true);
        root.setTop(topPane);
        root.setCenter(tabs.getTabPane());
        root.setRight(tabs.getObjectInspector());
        root.setBottom(statusBar.getStatusBar());
    }

    public void onLoadPlugin() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(getLastDirectory()));
        fileChooser.setTitle("Load Plugins");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Jar Files (*.jar)", "*.jar")
        );
        List<File> list = fileChooser.showOpenMultipleDialog(stage);
        if (list != null && !list.isEmpty()) {
            setLastDirectory(list.get(0));
            for (File file : list)
                doLoadPlugin(file.getAbsolutePath());
        }
    }

    public void doLoadPlugin(String filename) {
        try {
            this.setStatus("Loading Plugin " + filename + "...");
            PluginManager.loadPlugin(filename);
            ViewManager.loadPlugin(filename);
        } catch (Exception ex) {
            ExceptionBox exbox = new ExceptionBox();
            exbox.showAndWait(ex);
        }
        menu.update();
        this.setStatus("");
    }

    public void onNew(String structureName) throws Exception {
        Structure structure = StructureManager.instantiateStructure(structureName);
        tabs.addTab(structureName, structure);
        setStatus("created a " + structureName + "...");
    }

    public void onSave() {
        try {
            Structure structure = getCurrentStructure();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(getLastDirectory()));
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Graph Markup Language (*.graphml)", "*.graphml")
            );

            // add export-filters to list of extensions
            for (String format : ExportFilterManager.getExportFilters(structure.getClass())) {
                ExportFilterDescription descr = ExportFilterManager.getExportFilterDescription(structure.getClass(), format);
                ExtensionFilter filter = new FileChooser.ExtensionFilter(
                    descr.name() + " (*." + descr.fileExtension() + ")",
                    "*." + descr.fileExtension());
                fileChooser.getExtensionFilters().add(filter);
            }

            fileChooser.setTitle("Save File");
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                setLastDirectory(file);
                // has the user selected the native file-type or an export-filter?
                String extension = file.getName(); // unclean way of getting file extension
                int idx = extension.lastIndexOf('.');
                extension = idx > 0 ? extension.substring(idx + 1) : "";

                ExportFilter exportFilter = ExportFilterManager.instantiateExportFilterByExtension(structure.getClass(), extension);
                if (exportFilter != null) {
                    // configure export filter
                    ExportFilterParameters params = exportFilter.getParameters(structure);
                    if (params != null) {
                        ExportFilterStage exportStage = new ExportFilterStage(exportFilter, params, this);
                        exportStage.showAndWait();
                        if (!exportStage.dialogResult)
                            return;
                    }
                    exportFilter.exportGraph(structure, file.getAbsolutePath(), params);
                } else {
                    structure.writeToFile(file.getAbsolutePath());
                }
                tabs.setCurrentTabName(file.getName());
            }
        } catch (Exception ex) {
            ExceptionBox exbox = new ExceptionBox();
            exbox.showAndWait(ex);
        }
    }

    public void onOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(getLastDirectory()));
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("All (*.*)", "*.*"),
            new FileChooser.ExtensionFilter("Graph Markup Language (*.graphml)", "*.graphml")
        );

        // add export-filters to list of extensions
        for (String format : ImportFilterManager.getImportFilterClasses()) {
            ImportFilterDescription descr = ImportFilterManager.getImportFilterDescription(format);
            ExtensionFilter filter = new FileChooser.ExtensionFilter(
                descr.name() + " (*." + descr.fileExtension() + ")",
                "*." + descr.fileExtension());
            fileChooser.getExtensionFilters().add(filter);
        }

        fileChooser.setInitialFileName("*.*");
        fileChooser.setTitle("Open File");
        List<File> list = fileChooser.showOpenMultipleDialog(stage);
        if (list != null) {
            setLastDirectory(list.get(0));
            for (File file : list)
                doOpenFile(file);
        }
    }

    public void onDirectInput() throws Exception {
        DirectInputStage directinputstage = new DirectInputStage(this);
        directinputstage.showAndWait();
        Structure s = directinputstage.dialogResult;
        if (s != null)
            tabs.addTab("", s);
    }

    public void doOpenFile(File file) {
        try {
            // unclean way of getting file extension
            int idx = file.getName().lastIndexOf('.');
            String extension = idx > 0 ? file.getName().substring(idx + 1) : "";

            ImportFilter importFilter = ImportFilterManager.instantiateImportFilterByExtension(extension);
            this.setStatus("Loading File " + file.getAbsolutePath() + "...");
            Structure structure;

            if (importFilter != null) {
                ImportFilterParameters params = importFilter.getParameters();
                if (params != null) {
                    ImportFilterStage importStage = new ImportFilterStage(importFilter, params, this);
                    importStage.showAndWait();
                    if (!importStage.dialogResult) {
                        this.setStatus("");
                        return;
                    }
                }
                structure = importFilter.importGraph(file.getAbsolutePath(), params);
            } else {
                structure = Structure.loadFromFile(file.getAbsolutePath());
            }

            if (structure != null)
                tabs.addTab(file.getName(), structure);
        } catch (Exception ex) {
            ExceptionBox exbox = new ExceptionBox();
            exbox.showAndWait(ex);
        }
        this.setStatus("");
    }

    public void setStatus(String status) {
        statusBar.setStatus(status);
    }

    /**
     * Handler to generate a new graph.
     *
     * @param generatorName The name of the structure to generate.
     */
    public void onGenerate(String generatorName) {
        try {
            // prepare
            Generator gen = GeneratorManager.instantiateGenerator(generatorName);
            AlgorithmParameters params = gen.getParameters();
            if (params != null) {
                GeneratorStage genstage = new GeneratorStage(gen, params, this);
                genstage.showAndWait();
                if (!genstage.dialogResult)
                    return;
            }

            // run
            Structure genResult = gen.generate(params);
            if (genResult == null)
                return;
            tabs.addTab(gen.getDescription().name(), genResult);

        } catch (Exception ex) {
            this.setStatus("");
            ExceptionBox exbox = new ExceptionBox();
            exbox.showAndWait(ex);
        }
    }

    public void algorithmCompleted(StructurePane structurePane,
        AlgorithmThread algoThread) {
        try {
            if (algoThread.exception != null)
                throw algoThread.exception;

            Object algoResult = algoThread.result;
            this.setStatus("");

            // Show result if it is not null.
            if (algoResult == null) {
                structurePane.requestRedraw();
                return;
            }

            if (algoResult instanceof Structure) {
                tabs.addTab("Algorithm result", (Structure) algoResult);
            } else if (algoResult instanceof Set) {
                boolean isSelection = true;
                for (Object o : (Set) algoResult)
                    if (!(o instanceof Vertex)
                        && !(o instanceof Edge)
                        && !(o instanceof EdgeIntermediatePoint))
                        isSelection = false;
                if (isSelection) {
                    structurePane.selectAll((Set) algoResult);
                    structurePane.requestRedraw();
                }
            } else if (algoResult instanceof String) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Algorithm Result");
                alert.setHeaderText(null);
                alert.setContentText((String) algoResult);
                alert.showAndWait();
            } else {
                AlgorithmResultStage resultStage = new AlgorithmResultStage(
                    algoThread.algo,
                    algoThread.structure,
                    algoThread.params,
                    structurePane,
                    algoResult
                );
                resultStage.show();
            }
        } catch (Exception ex) {
            this.setStatus("");
            ExceptionBox exbox = new ExceptionBox();
            exbox.showAndWait(ex);
        }
    }

    public void onRunAlgorithm(String algorithmName) {
        try {
            // Prepare
            StructurePane structurePane = tabs.getCurrentStructurePane();
            Structure structure = structurePane.structure;
            Algorithm algo = AlgorithmManager.instantiateAlgorithm(structure.getClass(), algorithmName);

            AlgorithmParameters params = algo.getParameters(structure);
            if (params != null) {
                AlgorithmStage algostage = new AlgorithmStage(algo, structure, params, this);
                algostage.showAndWait();
                if (!algostage.dialogResult)
                    return;
            }

            // Run
            AlgorithmThread algoThread = new AlgorithmThread(
                algo, structure, params, structurePane.highlights.getSelection(),
                new RedrawOnProgress(structurePane, 1d / 60d));
            algoThread.setOnThreadComplete(t -> Platform.runLater(() -> {
                algorithmCompleted(structurePane, t);
            }));
            this.setStatus("Running Algorithm \"" + algorithmName + "\"...");
            algoThread.start();
        } catch (InvocationTargetException ex) {
            this.setStatus("");
            ExceptionBox exbox = new ExceptionBox();
            exbox.showAndWait((Exception) ex.getCause());
        } catch (Exception ex) {
            this.setStatus("");
            ExceptionBox exbox = new ExceptionBox();
            exbox.showAndWait(ex);
        }
    }

    // PROGRAM STARTUP
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            PluginManager.initialize();
            ViewManager.initialize();
            launch(args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(
            root,
            Preferences.getInteger(getClass(), "main-window-width", 1000),
            Preferences.getInteger(getClass(), "main-window-height", 800));
        this.stage = primaryStage;
        primaryStage.setTitle("Gralog");
        primaryStage.setScene(scene);
        primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWN, e -> windowShown());
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case S:
                    modeButtons.setSelectMode();
                    break;
                case V:
                    modeButtons.setVertexCreationMode();
                    break;
                case E:
                    modeButtons.setEdgeCreationMode();
                    break;
            }
        });
        // Remember the size of the window.
        primaryStage.setOnCloseRequest((e) -> {
            Preferences.setInteger(getClass(), "main-window-width", (int) scene.getWidth());
            Preferences.setInteger(getClass(), "main-window-height", (int) scene.getHeight());
        });
        primaryStage.show();
    }

    public void windowShown() {
        // Load Config
        NodeList children = null;
        String configFileDir = null;
        try {
            File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            File configFile = new File(jarFile.getParentFile().getAbsolutePath() + File.separator + "config.xml");
            configFileDir = configFile.getParent();

            if (configFile.exists()) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                try (FileInputStream input = new FileInputStream(configFile)) {
                    Document doc = dBuilder.parse(input);
                    doc.getDocumentElement().normalize();
                    children = doc.getDocumentElement().getChildNodes();
                }
            }
        } catch (IOException | URISyntaxException | ParserConfigurationException | SAXException ex) {
        }

        // Load Plugins from Config
        if (children != null)
            for (int i = 0; i < children.getLength(); ++i) {
                Node childNode = children.item(i);
                if (childNode.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                Element child = (Element) childNode;
                if (!child.getTagName().equals("plugin"))
                    continue;

                doLoadPlugin(configFileDir + File.separator + child.getAttribute("location"));
            }

        // Load Plugins from Parameters
        Application.Parameters params = this.getParameters();
        for (String s : params.getUnnamed())
            if (s.endsWith(".jar"))
                doLoadPlugin(s);

        // Open Files from Config
        if (children != null)
            for (int i = 0; i < children.getLength(); ++i) {
                Node childNode = children.item(i);
                if (childNode.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                Element child = (Element) childNode;
                if (!child.getTagName().equals("file"))
                    continue;

                doOpenFile(new File(child.getAttribute("location")));
            }

        // Open Files from Parameters
        for (String s : params.getUnnamed())
            if (!s.endsWith(".jar"))
                doOpenFile(new File(s));
    }

    private Structure getCurrentStructure() {
        StructurePane pane = tabs.getCurrentStructurePane();
        return pane == null ? null : pane.structure;
    }

    private void currentStructureChanged() {
        Structure structure = getCurrentStructure();
        menu.setCurrentStructure(structure);
        statusBar.setCurrentStructure(structure);
        modeButtons.setCurrentStructurePane(tabs.getCurrentStructurePane());
    }

    private String getLastDirectory() {
        final String defaultDir = System.getProperty("user.home");
        String dir = Preferences.getString(this.getClass(), "lastdirectory", defaultDir);
        if (Files.exists(Paths.get(dir)))
            return dir;
        return defaultDir;
    }

    private void setLastDirectory(File lastFile) {
        setLastDirectory(Paths.get(lastFile.getAbsolutePath()).getParent().toString());
    }

    private void setLastDirectory(String lastDirectory) {
        Preferences.setString(this.getClass(), "lastdirectory", lastDirectory);
    }
}
