package eu.immontilla.currencyfair.markettradeprocessor.resources;

import java.net.URI;

import javax.ws.rs.core.UriInfo;

final class HttpUtils {

    public static URI getCreatedResourceURI(UriInfo info, URI resourcePath, Long resourceId) {
        URI uri = info.getAbsolutePathBuilder().uri(resourcePath).path(info.getPath()).path(resourceId.toString()).build();
        return uri;
    }

}
