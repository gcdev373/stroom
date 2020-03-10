package stroom.node.api;

public final class NodeCallUtil {
    private NodeCallUtil() {
    }

    /**
     * @return True if the work should be executed on the local node.
     * I.e. if nodeName equals the name of the local node
     */
    public static boolean executeLocally(final NodeInfo nodeInfo,
                                         final String nodeName) {
        final String thisNodeName = nodeInfo.getThisNodeName();
        if (thisNodeName == null) {
            throw new RuntimeException("This node has no name");
        }

        // If this is the node that was contacted then just return our local info.
        return thisNodeName.equals(nodeName);
    }

    /**
     * @param nodeName The name of the node to get the base endpoint for
     * @return The base endpoint url for inter-node communications, e.g. http://some-fqdn:8080
     */
    public static String getBaseEndpointUrl(final NodeService nodeService, final String nodeName) {
        String url = nodeService.getBaseEndpointUrl(nodeName);
        if (url == null || url.isBlank()) {
            throw new RuntimeException("Remote node '" + nodeName + "' has no URL set");
        }
        // A normal url is something like "http://fqdn:8080"

        return url;
    }
}
