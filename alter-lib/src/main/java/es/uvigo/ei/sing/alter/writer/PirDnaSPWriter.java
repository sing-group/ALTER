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

import java.util.logging.Level;

import es.uvigo.ei.sing.alter.types.MSA;
import es.uvigo.ei.sing.alter.types.Protein;
import es.uvigo.ei.sing.alter.types.Type;
import es.uvigo.ei.sing.alter.types.Typeable;

/**
 * Extiende la clase PirWriter para adaptar la salida a dnaSP.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class PirDnaSPWriter extends PirWriter
{
    /**
     * Constructor de la clase. Llama al constructor de la superclase.
     * @param os Sistema operativo de salida.
     * @param lowerCase Salida en letras minúsculas.
     * @param match Salida codificada con caracteres match.
     * @param logger Nombre del logger a instanciar.
     */
    public PirDnaSPWriter(String os, boolean lowerCase, boolean match, String logger)
    {
        super(os,lowerCase,match,logger);
    }

    /**
     * Escribe un MSA en formato PIR adaptado a dnaSP. Para ello
     * comprueba que el MSA no sea de proteínas. Luego llama al método de la superclase.
     * @param msa MSA de entrada.
     * @return Cadena con la el MSA en formato PIR.
     */
    @Override
    public String write(MSA msa)
    {
        Type type = null;
        if (msa instanceof Typeable)
            type = ((Typeable) msa).getType();
        if (type == null)
        {
            type = WriterUtils.inferType(msa);
        }
        if (type instanceof Protein)
        {
            logger.log(Level.WARNING,"MSA is an amino acids MSA. " +
                    "It will not be processed by dnaSP (only DNA is processed).");
        }
        return super.write(msa);
    }
}
