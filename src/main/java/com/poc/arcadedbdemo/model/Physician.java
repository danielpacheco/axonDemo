package com.poc.arcadedbdemo.model;

import java.util.List;

public record Physician(int Id, String Name, List<Hospital> hospitals) {
}
