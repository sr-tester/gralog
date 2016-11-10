
//----------------------------------------------------
// The following code was generated by CUP v0.11b 20160615 (GIT 4ac7450)
//----------------------------------------------------

package gralog.firstorderlogic.logic.firstorder.parser;

import java_cup.runtime.*;
import gralog.algorithm.ParseError;
import gralog.firstorderlogic.logic.firstorder.formula.*;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java_cup.runtime.XMLElement;

/** CUP v0.11b 20160615 (GIT 4ac7450) generated parser.
  */
@SuppressWarnings({"rawtypes"})
public class FirstOrderParser extends java_cup.runtime.lr_parser {

 public final Class getSymbolContainer() {
    return FirstOrderScannerToken.class;
}

  /** Default constructor. */
  @Deprecated
  public FirstOrderParser() {super();}

  /** Constructor which sets the default scanner. */
  @Deprecated
  public FirstOrderParser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public FirstOrderParser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\024\000\002\002\003\000\002\002\004\000\002\002" +
    "\005\000\002\002\005\000\002\002\007\000\002\002\003" +
    "\000\002\005\005\000\002\005\003\000\002\006\005\000" +
    "\002\006\003\000\002\004\004\000\002\004\005\000\002" +
    "\004\006\000\002\003\004\000\002\003\006\000\002\003" +
    "\005\000\002\003\006\000\002\003\005\000\002\007\003" +
    "\000\002\007\005" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\046\000\014\004\006\006\014\011\011\012\007\015" +
    "\010\001\002\000\012\002\ufffa\005\ufffa\007\047\010\ufffa" +
    "\001\002\000\010\002\ufffc\005\ufffc\010\041\001\002\000" +
    "\014\004\006\006\014\011\011\012\007\015\010\001\002" +
    "\000\004\015\033\001\002\000\004\004\025\001\002\000" +
    "\004\015\021\001\002\000\004\002\020\001\002\000\006" +
    "\002\001\005\001\001\002\000\014\004\006\006\014\011" +
    "\011\012\007\015\010\001\002\000\012\002\ufff8\005\ufff8" +
    "\007\ufff8\010\ufff8\001\002\000\006\002\ufff4\005\ufff4\001" +
    "\002\000\012\002\ufff7\005\ufff7\007\ufff7\010\ufff7\001\002" +
    "\000\004\002\000\001\002\000\016\004\006\006\014\011" +
    "\011\012\007\014\023\015\010\001\002\000\006\002\ufff2" +
    "\005\ufff2\001\002\000\014\004\006\006\014\011\011\012" +
    "\007\015\010\001\002\000\006\002\ufff3\005\ufff3\001\002" +
    "\000\004\015\026\001\002\000\006\005\uffef\013\uffef\001" +
    "\002\000\006\005\031\013\030\001\002\000\004\015\032" +
    "\001\002\000\012\002\ufff5\005\ufff5\007\ufff5\010\ufff5\001" +
    "\002\000\006\005\uffee\013\uffee\001\002\000\016\004\006" +
    "\006\014\011\011\012\007\014\035\015\010\001\002\000" +
    "\006\002\ufff0\005\ufff0\001\002\000\014\004\006\006\014" +
    "\011\011\012\007\015\010\001\002\000\006\002\ufff1\005" +
    "\ufff1\001\002\000\004\005\040\001\002\000\012\002\ufff6" +
    "\005\ufff6\007\ufff6\010\ufff6\001\002\000\014\004\006\006" +
    "\014\011\011\012\007\015\010\001\002\000\006\002\uffff" +
    "\005\uffff\001\002\000\012\002\ufffb\005\ufffb\007\044\010" +
    "\ufffb\001\002\000\014\004\006\006\014\011\011\012\007" +
    "\015\010\001\002\000\006\002\ufffd\005\ufffd\001\002\000" +
    "\012\002\ufff9\005\ufff9\007\ufff9\010\ufff9\001\002\000\014" +
    "\004\006\006\014\011\011\012\007\015\010\001\002\000" +
    "\006\002\ufffe\005\ufffe\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\046\000\014\002\011\003\012\004\014\005\004\006" +
    "\003\001\001\000\002\001\001\000\002\001\001\000\014" +
    "\002\036\003\012\004\014\005\004\006\003\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\006\003\015\004\016\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\014\002\021\003\012\004\014\005" +
    "\004\006\003\001\001\000\002\001\001\000\014\002\023" +
    "\003\012\004\014\005\004\006\003\001\001\000\002\001" +
    "\001\000\004\007\026\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\014\002\033\003\012\004\014\005\004\006\003" +
    "\001\001\000\002\001\001\000\014\002\035\003\012\004" +
    "\014\005\004\006\003\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\010\003\041\004\014\006" +
    "\042\001\001\000\002\001\001\000\002\001\001\000\006" +
    "\003\044\004\045\001\001\000\002\001\001\000\002\001" +
    "\001\000\006\003\047\004\045\001\001\000\002\001\001" +
    "" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$FirstOrderParser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$FirstOrderParser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$FirstOrderParser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 1;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}




    String errorMsg = null;
    private String inputString;

    public Boolean hasError()
    {
        return errorMsg == null;
    }
    
    public String getErrorMsg()
    {
        if(errorMsg == null)
            return "no error";
        else
            return errorMsg;
    }

    @Override
    public void report_error(String message, Object info) {
    }

    @Override
    public void syntax_error(Symbol cur_token)
    {
        if(errorMsg == null)
            errorMsg = "Syntax Error: " + cur_token.toString();
    }

    @Override
    public void report_fatal_error(String message, Object info) throws ParseError
    {
        java_cup.runtime.ComplexSymbolFactory.ComplexSymbol symbol = (java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)info;
        throw new ParseError("Unexpected " + symbol.getName(), inputString, symbol.xleft.getColumn());
    }

    public FirstOrderFormula parseString(String str) throws Exception
    {
        String charset = "UTF8";
        byte[] bytes = str.getBytes(charset);
        ByteArrayInputStream stringstream = new ByteArrayInputStream(bytes);

        inputString = str;
        FirstOrderScanner scanner = new FirstOrderScanner(stringstream);
        this.setScanner(scanner);

        Symbol parserresult = this.parse();
        return (FirstOrderFormula) parserresult.value;
    }


/** Cup generated class to encapsulate user supplied action code.*/
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
class CUP$FirstOrderParser$actions {
  private final FirstOrderParser parser;

  /** Constructor */
  CUP$FirstOrderParser$actions(FirstOrderParser parser) {
    this.parser = parser;
  }

  /** Method 0 with the actual generated action code for actions 0 to 300. */
  public final java_cup.runtime.Symbol CUP$FirstOrderParser$do_action_part00000000(
    int                        CUP$FirstOrderParser$act_num,
    java_cup.runtime.lr_parser CUP$FirstOrderParser$parser,
    java.util.Stack            CUP$FirstOrderParser$stack,
    int                        CUP$FirstOrderParser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$FirstOrderParser$result;

      /* select the action based on the action number */
      switch (CUP$FirstOrderParser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // formula ::= quantifiedformula 
            {
              FirstOrderFormula RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).right;
		FirstOrderFormula f = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.peek()).value;
		 RESULT = f; 
              CUP$FirstOrderParser$result = parser.getSymbolFactory().newSymbol("formula",0, ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), RESULT);
            }
          return CUP$FirstOrderParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= formula EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-1)).right;
		FirstOrderFormula start_val = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-1)).value;
		RESULT = start_val;
              CUP$FirstOrderParser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-1)), ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$FirstOrderParser$parser.done_parsing();
          return CUP$FirstOrderParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // formula ::= disjunction OR quantifiedformula 
            {
              FirstOrderFormula RESULT =null;
		int leftleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).left;
		int leftright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).right;
		FirstOrderFormula left = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).value;
		int rightleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).left;
		int rightright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).right;
		FirstOrderFormula right = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.peek()).value;
		 RESULT = new FirstOrderOr(left, right); 
              CUP$FirstOrderParser$result = parser.getSymbolFactory().newSymbol("formula",0, ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)), ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), RESULT);
            }
          return CUP$FirstOrderParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // formula ::= conjunction AND quantifiedformula 
            {
              FirstOrderFormula RESULT =null;
		int leftleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).left;
		int leftright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).right;
		FirstOrderFormula left = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).value;
		int rightleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).left;
		int rightright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).right;
		FirstOrderFormula right = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.peek()).value;
		 RESULT = new FirstOrderAnd(left, right); 
              CUP$FirstOrderParser$result = parser.getSymbolFactory().newSymbol("formula",0, ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)), ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), RESULT);
            }
          return CUP$FirstOrderParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // formula ::= disjunction OR conjunction AND quantifiedformula 
            {
              FirstOrderFormula RESULT =null;
		int leftleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-4)).left;
		int leftright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-4)).right;
		FirstOrderFormula left = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-4)).value;
		int middleleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).left;
		int middleright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).right;
		FirstOrderFormula middle = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).value;
		int rightleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).left;
		int rightright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).right;
		FirstOrderFormula right = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.peek()).value;
		 RESULT = new FirstOrderOr(left, new FirstOrderAnd(middle, right)); 
              CUP$FirstOrderParser$result = parser.getSymbolFactory().newSymbol("formula",0, ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-4)), ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), RESULT);
            }
          return CUP$FirstOrderParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // formula ::= disjunction 
            {
              FirstOrderFormula RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).right;
		FirstOrderFormula f = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.peek()).value;
		 RESULT = f; 
              CUP$FirstOrderParser$result = parser.getSymbolFactory().newSymbol("formula",0, ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), RESULT);
            }
          return CUP$FirstOrderParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // disjunction ::= disjunction OR conjunction 
            {
              FirstOrderFormula RESULT =null;
		int leftleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).left;
		int leftright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).right;
		FirstOrderFormula left = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).value;
		int rightleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).left;
		int rightright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).right;
		FirstOrderFormula right = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.peek()).value;
		 RESULT = new FirstOrderOr(left, right); 
              CUP$FirstOrderParser$result = parser.getSymbolFactory().newSymbol("disjunction",3, ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)), ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), RESULT);
            }
          return CUP$FirstOrderParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // disjunction ::= conjunction 
            {
              FirstOrderFormula RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).right;
		FirstOrderFormula f = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.peek()).value;
		 RESULT = f; 
              CUP$FirstOrderParser$result = parser.getSymbolFactory().newSymbol("disjunction",3, ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), RESULT);
            }
          return CUP$FirstOrderParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // conjunction ::= conjunction AND basicformula 
            {
              FirstOrderFormula RESULT =null;
		int leftleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).left;
		int leftright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).right;
		FirstOrderFormula left = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).value;
		int rightleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).left;
		int rightright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).right;
		FirstOrderFormula right = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.peek()).value;
		 RESULT = new FirstOrderAnd(left, right); 
              CUP$FirstOrderParser$result = parser.getSymbolFactory().newSymbol("conjunction",4, ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)), ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), RESULT);
            }
          return CUP$FirstOrderParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // conjunction ::= basicformula 
            {
              FirstOrderFormula RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).right;
		FirstOrderFormula f = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.peek()).value;
		 RESULT = f; 
              CUP$FirstOrderParser$result = parser.getSymbolFactory().newSymbol("conjunction",4, ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), RESULT);
            }
          return CUP$FirstOrderParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // basicformula ::= NEG basicformula 
            {
              FirstOrderFormula RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).right;
		FirstOrderFormula f = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.peek()).value;
		 RESULT = new FirstOrderNot(f); 
              CUP$FirstOrderParser$result = parser.getSymbolFactory().newSymbol("basicformula",2, ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-1)), ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), RESULT);
            }
          return CUP$FirstOrderParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // basicformula ::= OPEN formula CLOSE 
            {
              FirstOrderFormula RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-1)).left;
		int fright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-1)).right;
		FirstOrderFormula f = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-1)).value;
		 RESULT = f; 
              CUP$FirstOrderParser$result = parser.getSymbolFactory().newSymbol("basicformula",2, ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)), ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), RESULT);
            }
          return CUP$FirstOrderParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // basicformula ::= STRING OPEN parameters CLOSE 
            {
              FirstOrderFormula RESULT =null;
		int relleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-3)).left;
		int relright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-3)).right;
		String rel = (String)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-3)).value;
		int paramsleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-1)).left;
		int paramsright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-1)).right;
		List<String> params = (List<String>)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-1)).value;
		 RESULT = new FirstOrderRelation(rel, params); 
              CUP$FirstOrderParser$result = parser.getSymbolFactory().newSymbol("basicformula",2, ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-3)), ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), RESULT);
            }
          return CUP$FirstOrderParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // quantifiedformula ::= NEG quantifiedformula 
            {
              FirstOrderFormula RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).right;
		FirstOrderFormula f = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.peek()).value;
		 RESULT = new FirstOrderNot(f); 
              CUP$FirstOrderParser$result = parser.getSymbolFactory().newSymbol("quantifiedformula",1, ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-1)), ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), RESULT);
            }
          return CUP$FirstOrderParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // quantifiedformula ::= EXISTS STRING DOT formula 
            {
              FirstOrderFormula RESULT =null;
		int varleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).left;
		int varright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).right;
		String var = (String)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).value;
		int fleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).right;
		FirstOrderFormula f = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.peek()).value;
		 RESULT = new FirstOrderExists(var, f); 
              CUP$FirstOrderParser$result = parser.getSymbolFactory().newSymbol("quantifiedformula",1, ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-3)), ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), RESULT);
            }
          return CUP$FirstOrderParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // quantifiedformula ::= EXISTS STRING formula 
            {
              FirstOrderFormula RESULT =null;
		int varleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-1)).left;
		int varright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-1)).right;
		String var = (String)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-1)).value;
		int fleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).right;
		FirstOrderFormula f = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.peek()).value;
		 RESULT = new FirstOrderExists(var, f); 
              CUP$FirstOrderParser$result = parser.getSymbolFactory().newSymbol("quantifiedformula",1, ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)), ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), RESULT);
            }
          return CUP$FirstOrderParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // quantifiedformula ::= FORALL STRING DOT formula 
            {
              FirstOrderFormula RESULT =null;
		int varleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).left;
		int varright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).right;
		String var = (String)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).value;
		int fleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).right;
		FirstOrderFormula f = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.peek()).value;
		 RESULT = new FirstOrderForall(var, f); 
              CUP$FirstOrderParser$result = parser.getSymbolFactory().newSymbol("quantifiedformula",1, ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-3)), ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), RESULT);
            }
          return CUP$FirstOrderParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // quantifiedformula ::= FORALL STRING formula 
            {
              FirstOrderFormula RESULT =null;
		int varleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-1)).left;
		int varright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-1)).right;
		String var = (String)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-1)).value;
		int fleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).right;
		FirstOrderFormula f = (FirstOrderFormula)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.peek()).value;
		 RESULT = new FirstOrderForall(var, f); 
              CUP$FirstOrderParser$result = parser.getSymbolFactory().newSymbol("quantifiedformula",1, ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)), ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), RESULT);
            }
          return CUP$FirstOrderParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // parameters ::= STRING 
            {
              List<String> RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).right;
		String s = (String)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.peek()).value;
		 RESULT = new ArrayList<String>(); RESULT.add(s); 
              CUP$FirstOrderParser$result = parser.getSymbolFactory().newSymbol("parameters",5, ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), RESULT);
            }
          return CUP$FirstOrderParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // parameters ::= parameters COMMA STRING 
            {
              List<String> RESULT =null;
		int paramsleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).left;
		int paramsright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).right;
		List<String> params = (List<String>)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)).value;
		int sleft = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()).right;
		String s = (String)((java_cup.runtime.Symbol) CUP$FirstOrderParser$stack.peek()).value;
		 RESULT = params; RESULT.add(s); 
              CUP$FirstOrderParser$result = parser.getSymbolFactory().newSymbol("parameters",5, ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.elementAt(CUP$FirstOrderParser$top-2)), ((java_cup.runtime.Symbol)CUP$FirstOrderParser$stack.peek()), RESULT);
            }
          return CUP$FirstOrderParser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number "+CUP$FirstOrderParser$act_num+"found in internal parse table");

        }
    } /* end of method */

  /** Method splitting the generated action code into several parts. */
  public final java_cup.runtime.Symbol CUP$FirstOrderParser$do_action(
    int                        CUP$FirstOrderParser$act_num,
    java_cup.runtime.lr_parser CUP$FirstOrderParser$parser,
    java.util.Stack            CUP$FirstOrderParser$stack,
    int                        CUP$FirstOrderParser$top)
    throws java.lang.Exception
    {
              return CUP$FirstOrderParser$do_action_part00000000(
                               CUP$FirstOrderParser$act_num,
                               CUP$FirstOrderParser$parser,
                               CUP$FirstOrderParser$stack,
                               CUP$FirstOrderParser$top);
    }
}

}