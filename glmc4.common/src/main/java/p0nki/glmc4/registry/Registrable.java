package p0nki.glmc4.registry;

import p0nki.glmc4.utils.Identifiable;

public abstract class Registrable<T extends Registrable<T> & Identifiable> implements Identifiable {

    public abstract Registry<T> getRegistry();

    public final Registry<T>.RegistryEntry getRegistryEntry() {
        return getRegistry().get(getID());
    }

    public final int getRawID() {
        return getRegistry().get(getID()).getRawID();
    }

}
