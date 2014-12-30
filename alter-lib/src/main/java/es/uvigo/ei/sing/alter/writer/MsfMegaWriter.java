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

import es.uvigo.ei.sing.alter.types.Sequence;

/**
 * Extends class MsfWriter to adapt to the output to MEGA.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class MsfMegaWriter extends MsfWriter
{
    /**
     * Class constructor. Calls the superclass constructor.
     * @param os Output operating system.
     * @param lowerCase Lowercase output.
     * @param logger Logger name.
     */
    public MsfMegaWriter(String os, boolean lowerCase, String logger)
    {
        super(os,lowerCase,logger);
    }

    /**
     * Returns the sequence data, using lowercase characters if necessary.
     * @param seq Sequence containing the data.
     * @param first First sequence in the MSA.
     * @return Sequence data.
     */
    @Override
    protected String getData(Sequence seq)
    {
        if (lowerCase)
            return seq.getData().toLowerCase();
        else
            return seq.getData();
    }
}
