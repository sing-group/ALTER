/* Generated By:JavaCC: Do not edit this line. PirParser.java */
package es.uvigo.ei.sing.alter.parser;

import es.uvigo.ei.sing.alter.types.Pir;
import es.uvigo.ei.sing.alter.types.PirSequence;
import java.io.StringReader;
import java.util.Vector;

/**
* PIR format parser.
* @author Daniel Gomez Blanco
* @version 1.2
*/
public class PirParser implements PirParserConstants {
    /**
    * Static method that parses an input string and returns a MSA in PIR format.
    * @param in Input string.
    * @return MSA in PIR format.
    */
    public static Pir parseMSA (String in) throws ParseException
    {
        //Parse string and return MSA
        PirParser parser = new PirParser(new StringReader(in));
        return parser.Pir();
    }

/**
* Grammar's root production. Structure:<br>
* &nbsp;&nbsp;1. Any combination of spaces, tabs and new lines until ">".<br>
* &nbsp;&nbsp;2. Sequences with the following format:<br>
* &nbsp;&nbsp;&nbsp;&nbsp;2a. ">".<br>
* &nbsp;&nbsp;&nbsp;&nbsp;2b. Sequence type.<br>
* &nbsp;&nbsp;&nbsp;&nbsp;2c. ";".<br>
* &nbsp;&nbsp;&nbsp;&nbsp;2d. Sequence identifier.<br>
* &nbsp;&nbsp;&nbsp;&nbsp;2e. New line.<br>
* &nbsp;&nbsp;&nbsp;&nbsp;2f. Description.<br>
* &nbsp;&nbsp;&nbsp;&nbsp;2g. New line.<br>
* &nbsp;&nbsp;&nbsp;&nbsp;2h. Sequence data.<br>
* @return MSA in PIR format.
*/
  final public Pir Pir() throws ParseException {
    Vector<PirSequence> seqs =  new Vector<PirSequence>();
    String type, id, desc, data;
    desc = "";
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EOL:
      case BLANK:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EOL:
        jj_consume_token(EOL);
        break;
      case BLANK:
        jj_consume_token(BLANK);
        break;
      default:
        jj_la1[1] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    label_2:
    while (true) {
      jj_consume_token(7);
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case BLANK:
          ;
          break;
        default:
          jj_la1[2] = jj_gen;
          break label_3;
        }
        jj_consume_token(BLANK);
      }
      type = Type();
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case BLANK:
          ;
          break;
        default:
          jj_la1[3] = jj_gen;
          break label_4;
        }
        jj_consume_token(BLANK);
      }
      jj_consume_token(8);
      id = Line();
      jj_consume_token(EOL);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BLANK:
      case UPPER_CASE:
      case LOWER_CASE:
      case NUMBER:
      case ANY:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
        desc = Line();
        jj_consume_token(EOL);
        break;
      case EOL:
        jj_consume_token(EOL);
        break;
      default:
        jj_la1[4] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      data = Data();
      jj_consume_token(9);
      label_5:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case EOL:
        case BLANK:
          ;
          break;
        default:
          jj_la1[5] = jj_gen;
          break label_5;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case EOL:
          jj_consume_token(EOL);
          break;
        case BLANK:
          jj_consume_token(BLANK);
          break;
        default:
          jj_la1[6] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
            seqs.add(new PirSequence(id,type,desc,data));
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 7:
        ;
        break;
      default:
        jj_la1[7] = jj_gen;
        break label_2;
      }
    }
    jj_consume_token(0);
        {if (true) return new Pir(seqs);}
    throw new Error("Missing return statement in function");
  }

/**
* Parses a PIR style type.
* @return Type.
*/
  final public String Type() throws ParseException {
    Token t1, t2;
    t1 = jj_consume_token(UPPER_CASE);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case UPPER_CASE:
      t2 = jj_consume_token(UPPER_CASE);
      break;
    case NUMBER:
      t2 = jj_consume_token(NUMBER);
      break;
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
        {if (true) return t1.image + t2.image;}
    throw new Error("Missing return statement in function");
  }

/**
* Parses a line made up of any character sequence.
* @return Parsed line.
*/
  final public String Line() throws ParseException {
    StringBuffer s = new StringBuffer();
    Token t;
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BLANK:
        t = jj_consume_token(BLANK);
        break;
      case UPPER_CASE:
        t = jj_consume_token(UPPER_CASE);
        break;
      case LOWER_CASE:
        t = jj_consume_token(LOWER_CASE);
        break;
      case NUMBER:
        t = jj_consume_token(NUMBER);
        break;
      case ANY:
        t = jj_consume_token(ANY);
        break;
      case 7:
        t = jj_consume_token(7);
        break;
      case 8:
        t = jj_consume_token(8);
        break;
      case 9:
        t = jj_consume_token(9);
        break;
      case 10:
        t = jj_consume_token(10);
        break;
      case 11:
        t = jj_consume_token(11);
        break;
      case 12:
        t = jj_consume_token(12);
        break;
      default:
        jj_la1[9] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
            s.append(t.image);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BLANK:
      case UPPER_CASE:
      case LOWER_CASE:
      case NUMBER:
      case ANY:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
        ;
        break;
      default:
        jj_la1[10] = jj_gen;
        break label_6;
      }
    }
        {if (true) return s.toString().trim();}
    throw new Error("Missing return statement in function");
  }

/**
* Parses sequence data. These data can be made up of any characters
* from "A" to "Z" (lowercase and uppercase), "-", "." or "?".
* Spaces, tabs and new lines are omitted.
* @return String with the sequence data (uppercase always).
*/
  final public String Data() throws ParseException {
    StringBuffer s = new StringBuffer();
    Token t;
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EOL:
      case BLANK:
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case BLANK:
          t = jj_consume_token(BLANK);
          break;
        case EOL:
          t = jj_consume_token(EOL);
          break;
        default:
          jj_la1[11] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        break;
      case UPPER_CASE:
      case LOWER_CASE:
      case 10:
      case 11:
      case 12:
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case UPPER_CASE:
          t = jj_consume_token(UPPER_CASE);
          break;
        case LOWER_CASE:
          t = jj_consume_token(LOWER_CASE);
          break;
        case 10:
          t = jj_consume_token(10);
          break;
        case 11:
          t = jj_consume_token(11);
          break;
        case 12:
          t = jj_consume_token(12);
          break;
        default:
          jj_la1[12] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
                s.append(t.image);
        break;
      default:
        jj_la1[13] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EOL:
      case BLANK:
      case UPPER_CASE:
      case LOWER_CASE:
      case 10:
      case 11:
      case 12:
        ;
        break;
      default:
        jj_la1[14] = jj_gen;
        break label_7;
      }
    }
        {if (true) return s.toString().toUpperCase();}
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public PirParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[15];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x6,0x6,0x4,0x4,0x1ffe,0x6,0x6,0x80,0x28,0x1ffc,0x1ffc,0x6,0x1c18,0x1c1e,0x1c1e,};
   }

  /** Constructor with InputStream. */
  public PirParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public PirParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new PirParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public PirParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new PirParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public PirParser(PirParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(PirParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[13];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 15; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 13; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
