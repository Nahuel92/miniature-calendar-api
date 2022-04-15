package org.nahuelrodriguez.miniaturecalendarapi.entity;

import java.io.Serializable;
import java.net.URL;

public record Picture(URL url, String picture) implements Serializable {
}
