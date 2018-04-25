package org.softwire.training.cinemagic.helpers;

import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class RandomHelper {

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String prettyUid(int characters) {
        return Strings.padStart(
                new BigInteger(5 * characters, RANDOM).toString(32).toUpperCase(),
                characters,
                '0');
    }
}
