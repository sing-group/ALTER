/*
 *  This file is part of ALTER.
 *
 *  ALTER is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  ALTER is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with ALTER.  If not, see <http://www.gnu.org/licenses/>.
 */

package es.uvigo.ei.sing.alter.converter;

import java.util.*;

/**
 * Provides methods to check the supported programs and formats, as well as
 * the possible options for each one of them.
 * @author Daniel Gonzalez Peña
 */
public class ProgramOptions{

  /**
   * Supported operating systems.
   * @return List of strings containing the supported operating systems.
   */
  public static List<String> getSO(){
    List<String> so = new LinkedList<String>();
    so.add("windows");
    so.add("linux");
    so.add("macos");
    return so;

  }

  /**
   * Supported input programs.
   * @return List of strings containing the supported input programs.
   */
  public static List<String> getInputPrograms(){
    List<String> in_programs = new LinkedList<String>();
    in_programs.add("clustal");
    in_programs.add("mafft");
    in_programs.add("tcoffee");
    in_programs.add("muscle");
    in_programs.add("probcons");
    return in_programs;
  }

  /**
   * Supported input formats.
   * @return List of strings containing the supported input formats.
   */
  public static List<String> getInputFormats(){
    List<String> in_formats = new LinkedList<String>();
    in_formats.add("aln");
    in_formats.add("fasta");
    in_formats.add("gde");
    in_formats.add("msf");
    in_formats.add("nexus");
    in_formats.add("phylip");
    in_formats.add("pir");
    return in_formats;
  }

  /**
   * Supported output programs.
   * @return List of strings containing the supported output programs.
   */
  public static List<String> getOutputPrograms(){
    List<String> out_programs = new LinkedList<String>();
    out_programs.add("general");
    out_programs.add("jmodeltest");
    out_programs.add("mrbayes");
    out_programs.add("paml");
    out_programs.add("paup");
    out_programs.add("phyml");
    out_programs.add("prottest");
    out_programs.add("raxml");
    out_programs.add("tcs");
    out_programs.add("bioedit");
    out_programs.add("se-al");
    out_programs.add("mega");
    out_programs.add("mesquite");
    out_programs.add("splitstree");
    out_programs.add("dnasp");
    out_programs.add("codabc");
    out_programs.add("clustal");
    out_programs.add("mafft");
    out_programs.add("muscle");
    out_programs.add("probcons");
    out_programs.add("tcoffee");
    out_programs.add("gblocks");
    out_programs.add("seaview");
    out_programs.add("trimal");
    return out_programs;
  }

  /**
   * Supported output formats.
   * @return List of strings containing the supported output formats.
   */
  public static List<String> getOutputFormats(){
    List<String> out_formats = new LinkedList<String>();
    out_formats.add("aln");
    out_formats.add("fasta");
    out_formats.add("gde");
    out_formats.add("mega");
    out_formats.add("msf");
    out_formats.add("nexus");
    out_formats.add("phylip");
    out_formats.add("pir");
    return out_formats;
  }

  /**
   * Supported output options.
   * @return List of strings containing the supported output options.
   */
  public static List<String> getOutOptions(){
    List<String> out_options = new LinkedList<String>();
    out_options.add("sequential");
    out_options.add("interleaved");
    out_options.add("lowercase");
    out_options.add("match");
    out_options.add("residue");

    return out_options;
  }

  /**
   * Supported formats depending on the input program.
   * @param program Input program.
   * @return List of strings containing the supported input formats.
   */
  public static List<String> getInputProgramFormats(String program){
    List<String> formats = new LinkedList<String>();
    if (program.equalsIgnoreCase("clustal")){
      formats.add("aln");
      formats.add("fasta");
      formats.add("gde");
      formats.add("msf");
      formats.add("nexus");
      formats.add("phylip");
      formats.add("pir");
    }else if (program.equalsIgnoreCase("mafft")){
      formats.add("aln");
      formats.add("fasta");
    }else if (program.equalsIgnoreCase("tcoffee")){
      formats.add("aln");
      formats.add("fasta");
      formats.add("msf");
      formats.add("phylip");
      formats.add("pir");
    }else if (program.equalsIgnoreCase("muscle")){
      formats.add("aln");
      formats.add("fasta");
      formats.add("msf");
      formats.add("phylip");
    }else if (program.equalsIgnoreCase("probcons")){
      formats.add("aln");
      formats.add("fasta");
    }else{
      throw new IllegalArgumentException("Input program not found: "+program);
    }
    return formats;
  }

  /**
   * Supported formats depending on the output program.
   * @param program Output program.
   * @return List of strings containing the supported output formats.
   */
  public static List<String> getOutputProgramFormats(String program){
    List<String> formats = new LinkedList<String>();
    if (program.equalsIgnoreCase("jmodeltest")){
      formats.add("aln");
      formats.add("fasta");
      formats.add("msf");
      formats.add("nexus");
      formats.add("phylip");
      formats.add("pir");
    }else if (program.equalsIgnoreCase("mrbayes")){
      formats.add("nexus");
    }else if (program.equalsIgnoreCase("paml")){
      formats.add("nexus");
      formats.add("phylip");
    }else if (program.equalsIgnoreCase("paup")){
      formats.add("mega");
      formats.add("msf");
      formats.add("nexus");
      formats.add("phylip");
      formats.add("pir");
    }else if (program.equalsIgnoreCase("phyml")){
      formats.add("phylip");
    }else if (program.equalsIgnoreCase("prottest")){
      formats.add("nexus");
      formats.add("phylip");
    }else if (program.equalsIgnoreCase("raxml")){
      formats.add("phyplip");
    }else if (program.equalsIgnoreCase("tcs")){
      formats.add("nexus");
      formats.add("phylip");
    }else if (program.equalsIgnoreCase("codabc")){
      formats.add("phylip");
    }else if (program.equalsIgnoreCase("bioedit")){
      formats.add("aln");
      formats.add("fasta");
      formats.add("msf");
      formats.add("nexus");
      formats.add("phylip");
      formats.add("pir");
    }else if (program.equalsIgnoreCase("se-al")){
      formats.add("fasta");
      formats.add("gde");
      formats.add("nexus");
      formats.add("phylip");
      formats.add("pir");
    }else if (program.equalsIgnoreCase("mega")){
      formats.add("aln");
      formats.add("fasta");
      formats.add("mega");
      formats.add("msf");
      formats.add("nexus");
      formats.add("phylip");
      formats.add("pir");
    }else if (program.equalsIgnoreCase("mesquite")){
      formats.add("nexus");
    }else if (program.equalsIgnoreCase("splitstree")){
      formats.add("aln");
      formats.add("fasta");
      formats.add("nexus");
      formats.add("phylip");
    }else if (program.equalsIgnoreCase("dnasp")){
      formats.add("fasta");
      formats.add("mega");
      formats.add("nexus");
      formats.add("phylip");
      formats.add("pir");
    }else if (program.equalsIgnoreCase("clustal")){
      formats.add("aln");
      formats.add("fasta");
      formats.add("gde");
      formats.add("msf");
      formats.add("pir");
    }else if (program.equalsIgnoreCase("mafft")){
      formats.add("fasta");
    }else if (program.equalsIgnoreCase("muscle")){
      formats.add("fasta");
    }else if (program.equalsIgnoreCase("probcons")){
      formats.add("fasta");
    }else if (program.equalsIgnoreCase("tcoffee")){
      formats.add("aln");
      formats.add("fasta");
      formats.add("msf");
      formats.add("pir");
    }else if (program.equalsIgnoreCase("gblocks")){
      formats.add("fasta");
      formats.add("pir");
    }else if (program.equalsIgnoreCase("seaview")){
      formats.add("aln");
      formats.add("fasta");
      formats.add("msf");
      formats.add("nexus");
      formats.add("phylip");
    }else if (program.equalsIgnoreCase("trimal")){
      formats.add("aln");
      formats.add("fasta");
      formats.add("mega");
      formats.add("nexus");
      formats.add("phylip");
      formats.add("pir");
    }else if (program.equalsIgnoreCase("general")){
      formats.addAll(getOutputFormats());
    }else{
      throw new IllegalArgumentException("Output program not found: "+program);
    }
    return formats;
  }

  /**
   * Possible options depending on output program and format.
   * @param program Output program.
   * @param format Output format.
   * @return
   */
  public static List<String> getOutputProgramOptions(String program, String format){
    List<String> options = new LinkedList<String>();
    if (!getOutputPrograms().contains(program)){
      throw new IllegalArgumentException("Output program not found: "+program);
    }
    if (!getOutputFormats().contains(format)){
      throw new IllegalArgumentException("Output format not found: "+format);
    }
    if (!getOutputProgramFormats(program).contains(format)){
      throw new IllegalArgumentException("Output Program "+program+" does not work with "+format+" format");
    }


    //Almost all programs work with lowercase and match
    options.add("lowercase");
    options.add("match");

    if (program.equalsIgnoreCase("mrbayes")){
      options.remove("match");
    }else if (program.equalsIgnoreCase("splitstree")){
      options.remove("match");
    }else if (program.equalsIgnoreCase("dnasp") && format.equalsIgnoreCase("nexus")){
      options.remove("match");
    }else if (program.equalsIgnoreCase("clustal")){
      options.remove("match");
    }else if (program.equalsIgnoreCase("mafft")){
      options.remove("match");
    }else if (program.equalsIgnoreCase("tcoffee")){
      options.remove("match");
    }else if (program.equalsIgnoreCase("gblocks")){
      options.remove("match");
    }

    //All programs accepting ALN accept residue
    if (format.equalsIgnoreCase("aln")){
      options.add("residue");
    }

    if(format.equalsIgnoreCase("nexus")){
      options.add("sequential");
      if (program.equalsIgnoreCase("phyml")){
	options.remove("sequential");
      } else if (program.equalsIgnoreCase("raxml")){
	options.remove("sequential");
      } else if (program.equalsIgnoreCase("mega")){
	options.remove("sequential");
      } else if (program.equalsIgnoreCase("clustal")){
	options.remove("sequential");
      } else if (program.equalsIgnoreCase("mafft")){
	options.remove("sequential");
      } else if (program.equalsIgnoreCase("tcoffee")){
	options.remove("sequential");
      } else if (program.equalsIgnoreCase("trimal")){
	options.remove("sequential");
      }

      options.add("interleaved");
      if (program.equalsIgnoreCase("paml")){
	options.remove("interleaved");
      } else if (program.equalsIgnoreCase("phyml")){
	options.remove("interleaved");
      } else if (program.equalsIgnoreCase("prottest")){
	options.remove("interleaved");
      } else if (program.equalsIgnoreCase("raxml")){
	options.remove("interleaved");
      } else if (program.equalsIgnoreCase("tcs")){
	options.remove("interleaved");
      } else if (program.equalsIgnoreCase("codabc")){
        options.remove("interleaved");
      } else if (program.equalsIgnoreCase("clustal")){
	options.remove("interleaved");
      } else if (program.equalsIgnoreCase("mafft")){
	options.remove("interleaved");
      } else if (program.equalsIgnoreCase("tcoffee")){
	options.remove("interleaved");
      }
    }

    if(format.equalsIgnoreCase("phylip")){
      options.add("sequential");
      if (program.equalsIgnoreCase("mrbayes")){
	options.remove("sequential");
      } else if (program.equalsIgnoreCase("mesquite")){
	options.remove("sequential");
      } else if (program.equalsIgnoreCase("clustal")){
	options.remove("sequential");
      } else if (program.equalsIgnoreCase("mafft")){
	options.remove("sequential");
      } else if (program.equalsIgnoreCase("tcoffee")){
	options.remove("sequential");
      } else if (program.equalsIgnoreCase("seaview")){
	options.remove("sequential");
      }

      options.add("interleaved");
      if (program.equalsIgnoreCase("mrbayes")){
	options.remove("interleaved");
      } else if (program.equalsIgnoreCase("tcs")){
	options.remove("interleaved");
      } else if (program.equalsIgnoreCase("codabc")){
        options.remove("interleaved");
      } else if (program.equalsIgnoreCase("mesquite")){
	options.remove("interleaved");
      } else if (program.equalsIgnoreCase("clustal")){
	options.remove("interleaved");
      } else if (program.equalsIgnoreCase("mafft")){
	options.remove("interleaved");
      } else if (program.equalsIgnoreCase("tcoffee")){
	options.remove("interleaved");
      }
    }
    return options;
  }



}
