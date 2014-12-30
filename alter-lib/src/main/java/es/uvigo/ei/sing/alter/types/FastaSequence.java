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

package es.uvigo.ei.sing.alter.types;

/**
 * Sequence in FASTA format.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class FastaSequence extends Sequence implements Describable
{
    /**
     * Sequence description.
     */
    private String desc;

    /**
     * Class constructor.
     * @param id Sequence identifier.
     * @param desc Sequence description.
     * @param data Sequence data.
     */
    public FastaSequence(String id, String desc, String data)
    {
        super(id, data);
        this.desc = desc;
    }

    /**
     * Returns the sequence data.
     * @return Sequence data.
     */
    public String getDesc()
    {
        return desc;
    }
}
