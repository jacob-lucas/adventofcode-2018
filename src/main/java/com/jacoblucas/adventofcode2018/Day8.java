package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.utils.Utils;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

public class Day8 {

    @Data
    @RequiredArgsConstructor
    public static class Node {
        @NonNull private List<Node> children;
        @NonNull private List<Integer> metadata;

        public int getMetadataSum() {
            return metadata.stream().mapToInt(Integer::intValue).sum() + children.stream().mapToInt(Node::getMetadataSum).sum();
        }
    }

    static List<Node> parse(final IntStream numbers) {
        final Iterator<Integer> iterator = numbers.iterator();

        final List<Node> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(parseSingleNode(iterator));
        }

        return result;
    }

    private static Node parseSingleNode(final Iterator<Integer> iterator) {
        final int numChildren = iterator.next();
        final int numMetadata = iterator.next();

        final List<Node> children = new ArrayList<>();
        for (int i = 0; i < numChildren; i++) {
            children.add(parseSingleNode(iterator));
        }

        final List<Integer> metadata = new ArrayList<>();
        for (int i = 0; i < numMetadata; i++) {
            metadata.add(iterator.next());
        }

        return new Node(children, metadata);
    }

    public static void main(String[] args) throws IOException {
        final List<Node> nodes = parse(
                Arrays.stream(
                    Utils.read("day8.txt")
                        .iterator()
                        .next()
                        .split(" "))
                        .mapToInt(Integer::valueOf));

        final Node root = nodes.get(0);
        System.out.println(root.getMetadataSum());
    }

}
