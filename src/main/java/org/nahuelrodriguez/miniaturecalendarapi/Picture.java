package org.nahuelrodriguez.miniaturecalendarapi;

import java.io.Serializable;
import java.net.URL;

record Picture(URL url, String picture) implements Serializable {
}
