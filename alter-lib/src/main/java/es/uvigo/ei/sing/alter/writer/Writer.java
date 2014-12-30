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

import es.uvigo.ei.sing.alter.types.MSA;

/**
 * Define los métodos que deben implementar todos los escritores.
 * @author Daniel Gomez Blanco
 * @version 1.1
 */

public interface Writer
{
    /**
     * Devuelve una cadena con el MSA en un formato determinado.
     * @param msa MSA de entrada.
     * @return Cadena con el MSA formateado.
     */
    public String write(MSA msa);

    public int STRING_BUFFER_MSALENGTH = 10000;
    public int STRING_BUFFER_SEQLENGTH = 2000;
}
