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

import es.uvigo.ei.sing.alter.reader.*;
import es.uvigo.ei.sing.alter.writer.*;

/**
 * Implements the default factory for converters.
 * @author Daniel Gomez Blanco
 * @version 1.1
 */
public class DefaultFactory implements Factory
{

    /**
     * Returns an adequate converter for the specified options.
     * @param inO Input operating system.
     * @param inP Input program.
     * @param inF Input format.
     * @param autodetect Format autodetection.
     * @param collapse Collapse sequences to haplotypes.
     * @param gapsAsMissing Treat gaps as missing data when collapsing.
     * @param countMissing Count missing data as differences when collapsing.
     * @param limit Connection limit (sequences differing at <= l sites will be collapsed).
     * @param outO Output operating system.
     * @param outP Output program.
     * @param outF Output format.
     * @param lowerCase Lower case output.
     * @param resNumbers Output residue numbers (only ALN format).
     * @param sequential Sequential output (only NEXUS and PHYLIP formats).
     * @param match Output match characters.
     * @param logger Name of the logger to instantiate.
     * @return Adequate converter for the specified options.
     * @throws UnsupportedOperationException If the options combination is not possible.
     */
    public Converter getConverter(String inO, String inP, String inF, boolean autodetect,
            boolean collapse, boolean gapsAsMissing, boolean countMissing, int limit,
            String outO, String outP, String outF,
            boolean lowerCase, boolean resNumbers, boolean sequential, boolean match,
            String logger)
            throws UnsupportedOperationException
    {
        Reader reader = getReader(inO, inP, inF, autodetect, logger);
        Writer writer = getWriter(outO, outP, outF, lowerCase, resNumbers, sequential, match, logger);

        return new DefaultConverter(reader, writer, collapse, gapsAsMissing, countMissing, limit, logger);
    }

    /**
     * Returns an adequate reader for the specified input options.
     * @param inO Input operating system.
     * @param inP Input program.
     * @param inF Input format.
     * @param autodetect Format autodetection.
     * @param logger Name of the logger to instantiate.
     * @return Adequate reader for the specified input options.
     * @throws UnsupportedOperationException If the options combination is not possible.
     */
    public Reader getReader(String inO, String inP, String inF, boolean autodetect, String logger)
            throws UnsupportedOperationException
    {
        Reader reader;

        if (autodetect)
            reader = new AutodetectionReader(logger);
        else if (inO.toLowerCase().equals("windows") || inO.toLowerCase().equals("linux") || inO.toLowerCase().equals("macos"))
            if (inP.toLowerCase().equals("clustal"))
                if (inF.toLowerCase().equals("aln"))
                    reader = new AlnReader(logger);
                else if (inF.toLowerCase().equals("fasta"))
                    reader = new FastaReader(logger);
                else if (inF.toLowerCase().equals("gde"))
                    reader = new GdeReader(logger);
                else if (inF.toLowerCase().equals("msf"))
                    reader = new MsfReader(logger);
                else if (inF.toLowerCase().equals("nexus"))
                    reader = new NexusReader(logger);
                else if (inF.toLowerCase().equals("phylip"))
                    reader = new PhylipReader(logger);
                else if (inF.toLowerCase().equals("pir"))
                    reader = new PirReader(logger);
                else
                    throw new UnsupportedOperationException("Input format " + inF + " not supported by Clustal.");
            else if (inP.toLowerCase().equals("mafft"))
                if (inF.toLowerCase().equals("aln"))
                    reader = new AlnReader(logger);
                else if (inF.toLowerCase().equals("fasta"))
                    reader = new FastaReader(logger);
                else
                    throw new UnsupportedOperationException("Input format " + inF + " not supported by Mafft.");
            else if (inP.toLowerCase().equals("tcoffee"))
                if (inF.toLowerCase().equals("aln"))
                    reader = new AlnReader(logger);
                else if (inF.toLowerCase().equals("fasta"))
                    reader = new FastaReader(logger);
                else if (inF.toLowerCase().equals("msf"))
                    reader = new MsfReader(logger);
                else if (inF.toLowerCase().equals("phylip"))
                    reader = new PhylipReader(logger);
                else if (inF.toLowerCase().equals("pir"))
                    reader = new PirReader(logger);
                else
                    throw new UnsupportedOperationException("Input format " + inF + " not supported by TCoffee.");
            else if (inP.toLowerCase().equals("muscle"))
                if (inF.toLowerCase().equals("aln"))
                    reader = new AlnReader(logger);
                else if (inF.toLowerCase().equals("fasta"))
                    reader = new FastaReader(logger);
                else if (inF.toLowerCase().equals("msf"))
                    reader = new MsfReader(logger);
                else if (inF.toLowerCase().equals("phylip"))
                    reader = new PhylipReader(logger);
                else
                    throw new UnsupportedOperationException("Input format " + inF + " not supported by MUSCLE.");
            else if (inP.toLowerCase().equals("probcons"))
                if (inF.toLowerCase().equals("aln"))
                    reader = new AlnReader(logger);
                else if (inF.toLowerCase().equals("fasta"))
                    reader = new FastaReader(logger);
                else
                    throw new UnsupportedOperationException("Input format " + inF + " not supported by PROBCONS.");
            else
                throw new UnsupportedOperationException("Input program " + inP + " not supported.");
        else
            throw new UnsupportedOperationException("Input operating system " + inO + " not supported.");

        return reader;

    }

    /**
     * Returns an adequate writer for the specified output options.
     * @param outO Output operating system.
     * @param outP Output program.
     * @param outF Output format.
     * @param lowerCase Lower case output.
     * @param resNumbers Output residue numbers (only ALN format).
     * @param sequential Sequential output (only NEXUS and PHYLIP formats).
     * @param match Output match characters.
     * @param logger Name of the logger to instantiate.
     * @return Adequate writer for the specified output options.
     * @throws UnsupportedOperationException If the options combination is not possible.
     */
    public Writer getWriter(String outO, String outP, String outF,
            boolean lowerCase, boolean resNumbers, boolean sequential, boolean match,
            String logger)
            throws UnsupportedOperationException
    {
        Writer writer;

        if (outO.toLowerCase().equals("windows") || outO.toLowerCase().equals("linux") || outO.toLowerCase().equals("macos"))
            if (outP.toLowerCase().equals("jmodeltest"))
                if (outF.toLowerCase().equals("aln"))
                    writer = new AlnJModelTestWriter(outO.toLowerCase(), lowerCase, resNumbers, match, logger);
                else if (outF.toLowerCase().equals("fasta"))
                    writer = new FastaJModelTestWriter(outO.toLowerCase(), lowerCase, match, logger);
                else if (outF.toLowerCase().equals("msf"))
                    writer = new MsfJModelTestWriter(outO.toLowerCase(), lowerCase, logger);
                else if (outF.toLowerCase().equals("nexus"))
                    writer = new NexusJModelTestWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                else if (outF.toLowerCase().equals("phylip"))
                    writer = new PhylipJModelTestWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                else if (outF.toLowerCase().equals("pir"))
                    writer = new PirJModelTestWriter(outO.toLowerCase(), lowerCase, match, logger);
                else
                    throw new UnsupportedOperationException("Output format " + outF + " not supported by jModeltest.");
            else if (outP.toLowerCase().equals("prottest"))
                if (outF.toLowerCase().equals("phylip"))
                    writer = new PhylipProtTestWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                else if (outF.toLowerCase().equals("nexus"))
                    if (sequential)
                        writer = new NexusProtTestWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                    else
                        throw new UnsupportedOperationException("Interleaved NEXUS not supported by ProtTest.");
                else
                    throw new UnsupportedOperationException("Output format " + outF + " not supported by ProtTest.");
            else if (outP.toLowerCase().equals("paml"))
                if (outF.toLowerCase().equals("nexus"))
                    if (sequential)
                        writer = new NexusWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                    else
                        throw new UnsupportedOperationException("Interleaved NEXUS not supported by PAML.");
                else if (outF.toLowerCase().equals("phylip"))
                    writer = new PhylipPAMLWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                else
                    throw new UnsupportedOperationException("Output format " + outF + " not supported by PAML.");
            else if (outP.toLowerCase().equals("phyml"))
                if (outF.toLowerCase().equals("phylip"))
                    writer = new PhylipPhyMLWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                else
                    throw new UnsupportedOperationException("Output format " + outF + " not supported by PhyML.");
            else if (outP.toLowerCase().equals("paup"))
                if (outF.toLowerCase().equals("mega"))
                    writer = new MegaWriter(outO.toLowerCase(), lowerCase, match, logger);
                else if (outF.toLowerCase().equals("msf"))
                    writer = new MsfWriter(outO.toLowerCase(), lowerCase, logger);
                else if (outF.toLowerCase().equals("nexus"))
                    writer = new NexusWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                else if (outF.toLowerCase().equals("phylip"))
                    writer = new PhylipWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                else if (outF.toLowerCase().equals("pir"))
                    writer = new PirWriter(outO.toLowerCase(), lowerCase, match, logger);
                else
                    throw new UnsupportedOperationException("Output format " + outF + " not supported by PAUP.");
            else if (outP.toLowerCase().equals("raxml"))
                if (outF.toLowerCase().equals("phylip"))
                    writer = new PhylipRAxMLWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                else
                    throw new UnsupportedOperationException("Output format " + outF + " not supported by RAxML.");
            else if (outP.toLowerCase().equals("mrbayes"))
                if (match)
                    throw new UnsupportedOperationException("Match first not supported by MrBayes.");
                else
                    if (outF.toLowerCase().equals("nexus"))
                        writer = new NexusMrBayesWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                    else
                        throw new UnsupportedOperationException("Output format " + outF + " not supported by MrBayes.");
            else if (outP.toLowerCase().equals("tcs"))
                if (outF.toLowerCase().equals("nexus"))
                    if (sequential)
                        writer = new NexusTCSWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                    else
                        throw new UnsupportedOperationException("Interleaved NEXUS not supported by TCS.");
                else if (outF.toLowerCase().equals("phylip"))
                    if (sequential)
                        writer = new PhylipTCSWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                    else
                        throw new UnsupportedOperationException("Interleaved PHYLIP not supported by TCS.");
                else
                    throw new UnsupportedOperationException("Output format " + outF + " not supported by TCS.");
            else if (outP.toLowerCase().equals("codabc"))
                if (outF.toLowerCase().equals("phylip"))
                    if (sequential)
                        writer = new PhylipCodABCWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                    else
                        throw new UnsupportedOperationException("Interleaved PHYLIP not supported by CodABC.");
                else
                    throw new UnsupportedOperationException("Output format " + outF + " not supported by CodABC.");
            else if (outP.toLowerCase().equals("bioedit"))
                if (outF.toLowerCase().equals("aln"))
                    writer = new AlnWriter(outO.toLowerCase(), lowerCase, resNumbers, match, logger);
                else if (outF.toLowerCase().equals("fasta"))
                    writer = new FastaWriter(outO.toLowerCase(), lowerCase, match, logger);
                else if (outF.toLowerCase().equals("msf"))
                    writer = new MsfWriter(outO.toLowerCase(), lowerCase, logger);
                else if (outF.toLowerCase().equals("nexus"))
                    writer = new NexusWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                else if (outF.toLowerCase().equals("phylip"))
                    writer = new PhylipBioEditWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                else if (outF.toLowerCase().equals("pir"))
                    writer = new PirWriter(outO.toLowerCase(), lowerCase, match, logger);
                else
                    throw new UnsupportedOperationException("Output format " + outF + " not supported by BioEdit.");
            else if (outP.toLowerCase().equals("mega"))
                if (outF.toLowerCase().equals("aln"))
                    writer = new AlnWriter(outO.toLowerCase(), lowerCase, resNumbers, match, logger);
                else if (outF.toLowerCase().equals("fasta"))
                    writer = new FastaWriter(outO.toLowerCase(), lowerCase, match, logger);
                else if (outF.toLowerCase().equals("mega"))
                    writer = new MegaWriter(outO.toLowerCase(), lowerCase, match, logger);
                else if (outF.toLowerCase().equals("msf"))
                    writer = new MsfMegaWriter(outO.toLowerCase(), lowerCase, logger);
                else if (outF.toLowerCase().equals("nexus"))
                    if (sequential)
                        throw new UnsupportedOperationException("Sequential NEXUS not supported by MEGA.");
                    else
                        writer = new NexusWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                else if (outF.toLowerCase().equals("phylip"))
                    writer = new PhylipMegaWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                else if (outF.toLowerCase().equals("pir"))
                    writer = new PirWriter(outO.toLowerCase(), lowerCase, match, logger);
                else
                    throw new UnsupportedOperationException("Output format " + outF + " not supported by MEGA.");
            else if (outP.toLowerCase().equals("dnasp"))
                if (outF.toLowerCase().equals("fasta"))
                    writer = new FastaDnaSPWriter(outO.toLowerCase(), lowerCase, match, logger);
                else if (outF.toLowerCase().equals("mega"))
                    writer = new MegaDnaSPWriter(outO.toLowerCase(), lowerCase, match, logger);
                else if (outF.toLowerCase().equals("nexus"))
                    if (!match)
                        writer = new NexusDnaSPWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                    else
                        throw new UnsupportedOperationException("Match first not supported by NEXUS for dnaSP.");
                else if (outF.toLowerCase().equals("phylip"))
                    writer = new PhylipDnaSPWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                else if (outF.toLowerCase().equals("pir"))
                    writer = new PirDnaSPWriter(outO.toLowerCase(), lowerCase, match, logger);
                else
                    throw new UnsupportedOperationException("Output format " + outF + " not supported by dnaSP.");
            else if (outP.toLowerCase().equals("se-al"))
                if (outF.toLowerCase().equals("fasta"))
                    writer = new FastaWriter(outO.toLowerCase(), lowerCase, match, logger);
                else if (outF.toLowerCase().equals("gde"))
                    writer = new GdeSeAlWriter(outO.toLowerCase(), lowerCase, match, logger);
                else if (outF.toLowerCase().equals("msf"))
                    writer = new MsfWriter(outO.toLowerCase(), lowerCase, logger);
                else if (outF.toLowerCase().equals("nexus"))
                    writer = new NexusWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                else if (outF.toLowerCase().equals("phylip"))
                    writer = new PhylipWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                else if (outF.toLowerCase().equals("pir"))
                    writer = new PirWriter(outO.toLowerCase(), lowerCase, match, logger);
                else
                    throw new UnsupportedOperationException("Output format " + outF + " not supported by Se-Al.");
            else if (outP.toLowerCase().equals("mesquite"))
                if (outF.toLowerCase().equals("nexus"))
                    writer = new NexusMesquiteWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                else
                    throw new UnsupportedOperationException("Output format " + outF + " not supported by Mesquite.");
            else if (outP.toLowerCase().equals("splitstree"))
                if (match)
                    throw new UnsupportedOperationException("Match first not supported by SplitsTree.");
                else
                    if (outF.toLowerCase().equals("aln"))
                        writer = new AlnWriter(outO.toLowerCase(), lowerCase, resNumbers, match, logger);
                    else if (outF.toLowerCase().equals("fasta"))
                        writer = new FastaWriter(outO.toLowerCase(), lowerCase, match, logger);
                    else if (outF.toLowerCase().equals("nexus"))
                        writer = new NexusSplitsTreeWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                    else if (outF.toLowerCase().equals("phylip"))
                        writer = new PhylipSplitsTreeWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                    else
                        throw new UnsupportedOperationException("Output format " + outF + " not supported by SplitsTree.");
            else if (outP.toLowerCase().equals("clustal"))
                if (match)
                    throw new UnsupportedOperationException("Match first not supported by Clustal.");
                else
                    if (outF.toLowerCase().equals("aln"))
                        writer = new AlnWriter(outO.toLowerCase(), lowerCase, resNumbers, match, logger);
                    else if (outF.toLowerCase().equals("fasta"))
                        writer = new FastaWriter(outO.toLowerCase(), lowerCase, match, logger);
                    else if (outF.toLowerCase().equals("gde"))
                        writer = new GdeWriter(outO.toLowerCase(), lowerCase, match, logger);
                    else if (outF.toLowerCase().equals("msf"))
                        writer = new MsfWriter(outO.toLowerCase(), lowerCase, logger);
                    else if (outF.toLowerCase().equals("pir"))
                        writer = new PirWriter(outO.toLowerCase(), lowerCase, match, logger);
                    else
                        throw new UnsupportedOperationException("Output format " + outF + " not supported by Clustal.");
            else if (outP.toLowerCase().equals("mafft"))
                if (match)
                    throw new UnsupportedOperationException("Match first not supported by MAFFT.");
                else
                    if (outF.toLowerCase().equals("fasta"))
                        writer = new FastaWriter(outO.toLowerCase(), lowerCase, match, logger);
                    else
                        throw new UnsupportedOperationException("Output format " + outF + " not supported by MAFFT.");
            else if (outP.toLowerCase().equals("tcoffee"))
                if (match)
                    throw new UnsupportedOperationException("Match first not supported by TCoffee.");
                else
                    if (outF.toLowerCase().equals("aln"))
                        writer = new AlnWriter(outO.toLowerCase(), lowerCase, resNumbers, match, logger);
                    else if (outF.toLowerCase().equals("fasta"))
                        writer = new FastaWriter(outO.toLowerCase(), lowerCase, match, logger);
                    else if (outF.toLowerCase().equals("msf"))
                        writer = new MsfWriter(outO.toLowerCase(), lowerCase, logger);
                    else if (outF.toLowerCase().equals("pir"))
                        writer = new PirWriter(outO.toLowerCase(), lowerCase, match, logger);
                    else
                        throw new UnsupportedOperationException("Output format " + outF + " not supported by TCoffee.");
            else if (outP.toLowerCase().equals("gblocks"))
                if (!match)
                    if (outF.toLowerCase().equals("fasta"))
                        writer = new FastaWriter(outO.toLowerCase(), lowerCase, match, logger);
                    else if (outF.toLowerCase().equals("pir"))
                        writer = new PirWriter(outO.toLowerCase(), lowerCase, match, logger);
                    else
                        throw new UnsupportedOperationException("Output format " + outF + " not supported by Gblocks.");
                else
                    throw new UnsupportedOperationException("Match first not supported by Gblocks.");
            else if (outP.toLowerCase().equals("muscle"))
                if (outF.toLowerCase().equals("fasta"))
                    writer = new FastaWriter(outO.toLowerCase(), lowerCase, match, logger);
                else
                    throw new UnsupportedOperationException("Output format " + outF + " not supported by MUSCLE.");
            else if (outP.toLowerCase().equals("probcons"))
                if (outF.toLowerCase().equals("fasta"))
                    writer = new FastaWriter(outO.toLowerCase(), lowerCase, match, logger);
                else
                    throw new UnsupportedOperationException("Output format " + outF + " not supported by PROBCONS.");
            else if (outP.toLowerCase().equals("seaview"))
                if (outF.toLowerCase().equals("aln"))
                    writer = new AlnWriter(outO.toLowerCase(), lowerCase, resNumbers, match, logger);
                else if (outF.toLowerCase().equals("fasta"))
                    writer = new FastaWriter(outO.toLowerCase(), lowerCase, match, logger);
                else if (outF.toLowerCase().equals("msf"))
                    writer = new MsfSeaViewWriter(outO.toLowerCase(), lowerCase, logger);
                else if (outF.toLowerCase().equals("nexus"))
                    writer = new NexusWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                else if (outF.toLowerCase().equals("phylip"))
                    if (!sequential)
                        writer = new PhylipWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                    else
                        throw new UnsupportedOperationException("Sequential PHYLIP not supported by SeaView.");
                else
                    throw new UnsupportedOperationException("Output format " + outF + " not supported by SeaView.");
            else if (outP.toLowerCase().equals("trimal"))
                if (outF.toLowerCase().equals("aln"))
                    writer = new AlnWriter(outO.toLowerCase(), lowerCase, resNumbers, match, logger);
                else if (outF.toLowerCase().equals("fasta"))
                    writer = new FastaWriter(outO.toLowerCase(), lowerCase, match, logger);
                else if (outF.toLowerCase().equals("mega"))
                    writer = new MegaWriter(outO.toLowerCase(), lowerCase, match, logger);
                else if (outF.toLowerCase().equals("nexus"))
                    if (!sequential)
                        writer = new PhylipWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                    else
                        throw new UnsupportedOperationException("Sequential NEXUS not supported by trimAl.");
                else if (outF.toLowerCase().equals("phylip"))
                    writer = new PhylipWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                else if (outF.toLowerCase().equals("pir"))
                    writer = new PirWriter(outO.toLowerCase(), lowerCase, match, logger);
                else
                    throw new UnsupportedOperationException("Output format " + outF + " not supported by trimAl.");
            else if (outP.toLowerCase().equals("general"))
                if (outF.toLowerCase().equals("aln"))
                    writer = new AlnWriter(outO.toLowerCase(), lowerCase, resNumbers, match, logger);
                else if (outF.toLowerCase().equals("fasta"))
                    writer = new FastaWriter(outO.toLowerCase(), lowerCase, match, logger);
                else if (outF.toLowerCase().equals("gde"))
                    writer = new GdeWriter(outO.toLowerCase(), lowerCase, match, logger);
                else if (outF.toLowerCase().equals("mega"))
                    writer = new MegaWriter(outO.toLowerCase(), lowerCase, match, logger);
                else if (outF.toLowerCase().equals("msf"))
                    writer = new MsfWriter(outO.toLowerCase(), lowerCase, logger);
                else if (outF.toLowerCase().equals("nexus"))
                    writer = new NexusWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                else if (outF.toLowerCase().equals("phylip"))
                    writer = new PhylipWriter(outO.toLowerCase(), lowerCase, sequential, match, logger);
                else if (outF.toLowerCase().equals("pir"))
                    writer = new PirWriter(outO.toLowerCase(), lowerCase, match, logger);
                else
                    throw new UnsupportedOperationException("Output format " + outF + " not supported.");
            else
                throw new UnsupportedOperationException("Output program " + outP + " not supported.");
        else
            throw new UnsupportedOperationException("Output operating system " + outO + " not supported.");

        return writer;
    }
}
