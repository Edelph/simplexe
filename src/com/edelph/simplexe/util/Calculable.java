package com.edelph.simplexe.util;

import java.util.Optional;

public interface Calculable<T> {
    T plus(T thing);
    T less(T thing);
    T divide (T thing);
    T multiply (T thing);
    T exponent (int number);
    T square ();
    T inverse();
}
