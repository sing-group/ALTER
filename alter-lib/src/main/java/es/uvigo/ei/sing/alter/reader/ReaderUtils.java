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
import es.uvigo.ei.sing.alter.types.Sequence;

/**
 * Provides static methods common to every reader.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class ReaderUtils
{
    /**
     * Replaces match characters with the character in the same position of the
     * first sequence of the MSA.
     * @param seq Current sequence.
     * @param first First sequence of the MSA.
     * @throws ParseException If a match character is trying to be replaced by a "?", "." or "-" character.
     */
    public static void replaceMatch(Sequence seq, Sequence first) throws ParseException
    {
        if (seq.getData().contains("."))
            //Lanzar excepción si el caracter está en la primera secuencia
            if (seq == first)
                throw new ParseException("Match character \".\" in first sequence of MSA.");
            else
                for(int j=0;j<seq.getData().length();j++)
                    if (seq.getData().charAt(j) == '.')
                        if (first.getData().charAt(j) == '?'
                            || first.getData().charAt(j) == '-')
                            throw new ParseException("Match character \".\" cannot be replaced by \"?\" or \"-\".");
                        else
                            seq.replaceChar(j, first.getData().charAt(j));
    }
}
