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
 * Sequence in a MSA.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public abstract class Sequence
{
    /**
     * Sequence identifier.
     */
    private String id;
    /**
     * Sequence data.
     */
    private StringBuffer data;

    /**
     * Class constructor.
     * @param id Sequence identifier.
     * @param data Sequence data.
     */
    public Sequence(String id, String data)
    {
        this.id = id;
        this.data = new StringBuffer(2000);
        this.data.append(data);
    }

    /**
     * Returns the sequence identifier.
     * @return Sequence identifier.
     */
    public String getId()
    {
        return id;
    }

    /**
     * Returns the sequence data.
     * @return Sequence data.
     */
    public String getData()
    {
        return data.toString();
    }

    /**
     * Concats given data to the current data.
     * @param data Data to concat.
     */
    public void concat(String data)
    {
        this.data.append(data);
    }

    /**
     * Replaces the character in the given position for the given character.
     * @param index Index of the character to be replaced.
     * @param character Character to be inserted.
     */
    public void replaceChar(int index, char character)
    {
        this.data.setCharAt(index, character);
    }
}
