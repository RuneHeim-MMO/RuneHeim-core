package org.zuma.examplePlugin.domain.block.meta.v1;

import org.zuma.examplePlugin.domain.map.MarkerType;

import java.util.List;

public record InvisibleMarkerMeta(List<String> hologramContent, MarkerType markerType, String markerLabel) {
}
