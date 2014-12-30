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

package es.uvigo.ei.sing.alter.reader;

import es.uvigo.ei.sing.alter.parser.ParseException;
import es.uvigo.ei.sing.alter.types.MSA;

/**
 * Defines the method that readers must implement.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */
public interface Reader
{
    /**
     * Parses an input string and returns an MSA object.
     * @param in Input string.
     * @return MSA object parsed from the input string.
     * @throws ParseException If an error occurs while parsing.
     */
    public MSA read(String in) throws ParseException;
}
