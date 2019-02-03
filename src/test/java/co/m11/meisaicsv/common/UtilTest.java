package co.m11.meisaicsv.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static co.m11.meisaicsv.common.Util.md5;
import org.junit.jupiter.api.Test;

class UtilTest {

    @Test
    void testMd5() {
        assertEquals("35454B055CC325EA1AF2126E27707052", md5("ILoveJava"));
        assertEquals("D41D8CD98F00B204E9800998ECF8427E", md5(null));
    }
}
