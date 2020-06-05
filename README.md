# Author

Jeremy Vinding

# Summary

A very simple app to demonstrate the Hexagonal Architecture (Ports & Adapters) pattern.

# Notes

1. For demonstration purposes, packages, and classes are named for their roles in the Hexagonal Architecture pattern. These names do not, in any way, reflect the way things should be done in a "real" app.
2. There are several branches that show variations of the app. Although each branch builds upon the previous branch, one should not infer that each branch is *better* than the previous. Each of the demonstrated patterns may be valid for certain circumstances.
    1. 1-simple-hexagon - This is a very simple "pure" version of a Hexagonal app.
    2. 2-simple-with-use-cases - This one just adds "use cases" to the above app.
    3. 3-mediator - replace the primary port handler with a mediator pattern.
    4. 4-mediator-with-handlers - use a separate "handler" for each use case.
