package io.ipfs.api;

import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;

public record Peer(
        MultiAddress address,
        Multihash id,
        long latency,
        String muxer,
        Object streams
) {
}
