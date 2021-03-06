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
 * Sequence in GDE format.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class GdeSequence extends Sequence
{
    /**
     * Class constructor.
     * @param id Sequence identifier.
     * @param data Sequence data.
     */
    public GdeSequence(String id, String data)
    {
        super(id, data);
    }
}
