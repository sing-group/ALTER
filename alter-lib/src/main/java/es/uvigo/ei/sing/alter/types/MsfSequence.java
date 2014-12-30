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
 * Sequence in MSF format.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class MsfSequence extends Sequence implements Checksumable, Lengthable, Weightable
{
    /**
     * Sequence length.
     */
    private int length;
    /**
     * Sequence checksum.
     */
    private int check;
    /**
     * Sequence weight.
     */
    private float weight;

    /**
     * Class constructor.
     * @param id Sequence identifier.
     * @param length Sequence length.
     * @param check Sequence checksum.
     * @param weight Sequence weight.
     * @param data Sequence data.
     */
    public MsfSequence (String id, int length, int check, float weight, String data)
    {
        super(id, data);
        this.length = length;
        this.check = check;
        this.weight = weight;
    }

    /**
     * Returns the sequence checksum.
     * @return Sequence checksum.
     */
    public int getChecksum()
    {
        return check;
    }

    /**
     * Returns sequence length.
     * @return Sequence length.
     */
    public int getLength()
    {
        return length;
    }

    /**
     * Returns sequence weight.
     * @return Sequence weight.
     */
    public float getWeight()
    {
        return weight;
    }
}
