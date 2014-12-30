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

package es.uvigo.ei.sing.alter.writer;

/**
 * Extends class MsfWriter to adapt to the output to SeaView.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class MsfSeaViewWriter extends MsfWriter
{
    /**
     * Class constructor. Calls the superclass constructor.
     * @param os Output operating system.
     * @param lowerCase Lowercase output.
     * @param logger Logger name.
     */
    public MsfSeaViewWriter(String os, boolean lowerCase, String logger)
    {
        super(os,lowerCase,logger);
    }

    /**
     * Writes information of a sequence, to be placed in the information block
     * after the MSF header.
     * @param id Sequence identifier.
     * @param longestId Longest identifier length.
     * @param length Sequence length.
     * @param longestLength Longest length number length.
     * @param check Sequence checksum.
     * @param longestCheck Longest checksum length.
     * @param weight Sequence weight.
     * @param longestWeight Longest weight length.
     * @return Sequence information.
     */
    @Override
    protected String writeInfo(String id, int longestId, int length, int longestLength,
                                int check, int longestCheck, float weight, int longestWeight)
    {
        return " Name: " + id + WriterUtils.align(id.length(),longestId)
            + "  Len:  " + WriterUtils.align(String.valueOf(length).length(),longestLength) + length
            + "  Check:  " + WriterUtils.align((String.valueOf(check)).length(),longestCheck) + check
            + "  Weight:  " + WriterUtils.align(String.valueOf(weight).length(),longestWeight) + weight
            + nl;
    }
}
