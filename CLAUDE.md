# Agent Instructions

## Documentation style

Do not document things that are already clear from reading the code — model names, cost constants,
file paths, enum values, function signatures. These go stale and add noise.
Document *why* something is done, not *what* the code does.

When you make changes, any new or changed comment should only refer to the current state. 
Differences to the previous state belong only in the commit message (if at all).
