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
 * Sequence in PIR format.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class PirSequence extends Sequence implements Describable, Typeable
{
    /**
     * Sequence type. In PIR format, every sequence has an individual type
     * but it must be equal in all of them.
     */
    private Type type;
    /**
     * Sequence description.
     */
    private String desc;

    /**
     * Class constructor.
     * @param id Sequence identifier.
     * @param type Sequence type.
     * @param desc Sequence description.
     * @param data Sequence data.
     */
    public PirSequence(String id, String type, String desc, String data)
    {
        super(id,data);

        this.desc = desc;

        if (type.equals("P1"))
            this.type =  new ProteinComplete();
        else if (type.equals("F1"))
            this.type = new ProteinFragment();
        else if (type.equals("DL"))
            this.type = new DNALinear();
        else if (type.equals("DC"))
            this.type = new DNACircular();
        else if (type.equals("RL"))
            this.type = new RNALinear();
        else if (type.equals("RC"))
            this.type = new RNACircular();
        else if (type.equals("N3"))
            this.type = new RNAt();
        else if (type.equals("N1"))
            this.type = new RNAOther();
        else
            this.type = null;
    }

    /**
     * Returns the sequence type.
     * @return Sequence type.
     */
    public Type getType()
    {
        return type;
    }

    /**
     * Returns the sequence description.
     * @return Sequence description.
     */
    public String getDesc()
    {
        return desc;
    }
}
