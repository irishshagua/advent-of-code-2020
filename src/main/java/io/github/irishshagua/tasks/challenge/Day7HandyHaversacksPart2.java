package io.github.irishshagua.tasks.challenge;

import io.github.irishshagua.app.exceptions.TaskNotImplementedException;
import io.github.irishshagua.app.io.IO;
import io.github.irishshagua.tasks.AOCTaskEvaluation;
import io.github.irishshagua.tasks.TaskResult;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day7HandyHaversacksPart2 implements AOCTaskEvaluation {

    @Override
    public TaskResult run(Path input) throws TaskNotImplementedException {
        var locations = IO.readLinesAs(input, this::parse);
        var start = LocalDateTime.now();
        var result = algo(locations);
        var stop = LocalDateTime.now();

        if (result == -1) return new TaskResult(false, "Algo Didnt Work", Duration.between(start, stop));
        else return new TaskResult(true, "Result is: " + result, Duration.between(start, stop));
    }

    private Integer algo(List<BaggageRule> baggageRules) {
        var myBagColour = "shiny gold";
        var tree = buildTree(myBagColour, baggageRules);
        var numChildren = countBags(tree);
        return numChildren;
    }

    private Integer countBags(Node root) {
        var traversible = new LinkedList<Node>();
        traversible.add(root);
        int sum = 0;
        while(!traversible.isEmpty()) {
            var localNode= traversible.pop();
            for (Node child : localNode.children) {
                sum += 1;
                traversible.push(child);
            }
        }

        return sum;
    }

    private Node buildTree(String rootColour, List<BaggageRule> baggageRules) {
        var root = new Node(rootColour);
        var nodes = new LinkedList<Node>();
        nodes.add(root);
        while (!nodes.isEmpty()) {
            var localRoot = nodes.pop();
            for (BaggageRule rule : baggageRules) {
                if (rule.primaryColour().equals(localRoot.colour)) {
                    for (Map.Entry<String, Integer> kv : rule.allowedColours().entrySet()) {
                        for (int i = 0; i < kv.getValue(); i++) {
                            var child = new Node(kv.getKey());
                            nodes.add(child);
                            localRoot.addChild(child);
                        }
                    }
                }
            }
        }

        return root;
    }

    private BaggageRule parse(String line) {
        var primaryColourPattern = Pattern.compile("(\\w* \\w*) bags contain .*");
        var singleSecondaryPattern = Pattern.compile("(\\w* \\w*) bags contain (\\d*) (\\w* \\w*) bags?.");
        var furtherBagsPattern = Pattern.compile(".* (\\d*) (\\w* \\w*) bag.*");
        if (line.contains("no other bags")) {
            var matcher = primaryColourPattern.matcher(line);
            if (matcher.matches()) return new BaggageRule(matcher.group(1), Map.of());
            else throw new IllegalStateException("["+line+"] failed to match");
        } else if (line.contains(",")) {
            var parts = line.split(",");
            var map = new HashMap<String, Integer>();
            for (String part : parts) {
                var matcher = furtherBagsPattern.matcher(part);
                if (matcher.matches()) map.put(matcher.group(2), Integer.valueOf(matcher.group(1)));
                else if (line.contains("shiny tan")) System.out.println("Didnt Match");
            }
            var primaryMatcher = primaryColourPattern.matcher(line);
            if (primaryMatcher.matches()) return new BaggageRule(primaryMatcher.group(1), map);
            else throw new IllegalStateException("["+line+"] failed to match");
        }
        else {
            var matcher = singleSecondaryPattern.matcher(line);
            if (matcher.matches()) return new BaggageRule(matcher.group(1), Map.of(matcher.group(3), Integer.valueOf(matcher.group(2))));
            else throw new IllegalStateException("["+line+"] failed to match");
        }
    }
}