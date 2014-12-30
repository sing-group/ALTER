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

import es.uvigo.ei.sing.alter.reader.Reader;
import es.uvigo.ei.sing.alter.writer.Writer;

/**
 * Defines the methods needed to implement a converter factory.
 * @author Daniel Gomez Blanco
 * @version 1.1
 */
public interface Factory
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
            throws UnsupportedOperationException;

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
    public Reader getReader(String inO, String inP, String inF, boolean autodetect,
            String logger)
            throws UnsupportedOperationException;

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
            throws UnsupportedOperationException;
}
