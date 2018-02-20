package org.softwire.training.cinemagic.helpers;

import com.google.common.base.Strings;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class RandomHelper {

    // Non-instantiable
    private RandomHelper() {}

    private static final Random RAND = new SecureRandom();

    public static String prettyUid(int characters) {
        return Strings.padStart(
                new BigInteger(5 * characters, RAND).toString(32).toUpperCase(),
                characters,
                '0');
    }
}
