package eu.immontilla.currencyfair.markettradeprocessor.test.utils;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@SuppressWarnings("rawtypes")
public class ModelFactory<E> {
    Class theClass = null;

    public ModelFactory(Class theClass) {
        this.theClass = theClass;
    }

    @SuppressWarnings("unchecked")
    public E createInstance() throws IllegalAccessException, InstantiationException {
        return (E) this.theClass.newInstance();
    }

    @SuppressWarnings("unchecked")
    public E getObject() throws IllegalAccessException, InstantiationException {
        E obj = (E) this.theClass.newInstance();
        PodamFactory factory = new PodamFactoryImpl();
        return factory.manufacturePojo((Class<E>) obj.getClass());
    }

}
