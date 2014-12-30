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

package es.uvigo.ei.sing.alter.cmdLine;

import java.io.File;
import org.kohsuke.args4j.Option;

/**
 * Defines the options to be processed by args4j when ALTER is executed
 * from the command line. Java annotations are used.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */
public class CmdLineOptions
{
    /**
     * Inpute file.
     */
    @Option(name = "-i", usage = "Input file.", required = true, aliases = "--input")
    File in;
    /**
     * Input operating system (Linux, MacOS or Windows).
     */
    @Option(name = "-io", usage = "Input operating system (Linux, MacOS or Windows).",
    aliases = "--inputOS")
    String inOS;
    /**
     * Input program (Clustal, MAFFT, MUSCLE, PROBCONS or TCoffee).
     */
    @Option(name = "-ip", usage = "Input program (Clustal, MAFFT, MUSCLE, PROBCONS or TCoffee).",
    aliases = "--inputProgram")
    String inProgram;
    /**
     * Inpute format (ALN, FASTA, GDE, MSF, NEXUS, PHYLIP or PIR).
     */
    @Option(name = "-if", usage = "Input format (ALN, FASTA, GDE, MEGA, MSF, NEXUS, PHYLIP or PIR).",
    aliases = "--inputFormat")
    String inFormat;
    /**
     * Autodetect format (other input options are omitted).
     */
    @Option(name = "-ia", usage = "Autodetect format (other input options are omitted).",
    aliases = "--inputAutodetect")
    boolean autodetect = false;
    /**
     * Collapse sequences to haplotypes.
     */
    @Option(name = "-c", usage = "Collapse sequences to haplotypes.",
    aliases = "--collapse")
    boolean collapse = false;
    /**
     * Treat gaps as missing data when collapsing.
     */
    @Option(name = "-cg", usage = "Treat gaps as missing data when collapsing.",
    aliases = "--collapseGaps")
    boolean gaps = false;
    /**
     * Count missing data as differences when collapsing.
     */
    @Option(name = "-cm", usage = "Count missing data as differences when collapsing.",
    aliases = "--collapseMissing")
    boolean missing = true;
    /**
     * Connection limit (sequences differing at <= l sites will be collapsed) (default is l=0).
     */
    @Option(name = "-cl", usage = "Connection limit (sequences differing at <= l sites will be collapsed) (default is l=0).",
    aliases = "--collapseLimit")
    int limit = 0;
    /**
     * Output file.
     */
    @Option(name = "-o", usage = "Output file.", required = true, aliases = "--output")
    File out;
    /**
     * Output operating system (Linux, MacOS or Windows).
     */
    @Option(name = "-oo", usage = "Output operating system (Linux, MacOS or Windows).",
    required = true, aliases = "--outputOS")
    String outOS;
    /**
     * Output program (jModelTest, MrBayes, PAML, PAUP, PhyML, ProtTest, RAxML, TCS, CodABC, BioEdit, MEGA, dnaSP, Se-Al, Mesquite, SplitsTree, Clustal, MAFFT, MUSCLE, PROBCONS, TCoffee, Gblocks, SeaView, trimAl or GENERAL).
     */
    @Option(name = "-op", usage = "Output program (jModelTest, MrBayes, PAML, PAUP, PhyML, ProtTest, RAxML, TCS, CodABC, BioEdit, MEGA, dnaSP, Se-Al, Mesquite, SplitsTree, Clustal, MAFFT, MUSCLE, PROBCONS, TCoffee, Gblocks, SeaView, trimAl or GENERAL)",
    required = true, aliases = "--outputProgram")
    String outProgram;
    /**
     * Output format (ALN, FASTA, GDE, MEGA, MSF, NEXUS, PHYLIP or PIR).
     */
    @Option(name = "-of", usage = "Output format (ALN, FASTA, GDE, MEGA, MSF, NEXUS, PHYLIP or PIR).",
    required = true, aliases = "--outputFormat")
    String outFormat;
    /**
     * Lower case output.
     */
    @Option(name = "-ol", usage = "Lowe case output.", aliases = "--outputLowerCase")
    boolean lowerCase = false;
    /**
     * Output residue numbers (only ALN format).
     */
    @Option(name = "-on", usage = "Output residue numbers (only ALN format).",
    aliases = "--outputResidueNumbers")
    boolean residueNumbers = false;
    /**
     * Sequential output (only NEXUS and PHYLIP formats).
     */
    @Option(name = "-os", usage = "Sequential output (only NEXUS and PHYLIP formats).",
    aliases = "--outputSequential")
    boolean sequential = false;
    /**
     * Output match characters.
     */
    @Option(name = "-om", usage = "Output match characters.",
    aliases = "--outputMatch")
    boolean match = false;
}
