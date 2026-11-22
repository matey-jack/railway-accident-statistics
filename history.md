
Some history:
- 2026-02: the local LLM only did summarization for a lot of the smaller input files, because it just took too 
  long. Given how Gemma 31B choked on the largest input file, the local model might also have failed on that one.
- 2026-03: the local LLM also ever only used the worst of all the PDF extractors. With Bedrock I first used the 
  file-content-based PDF extraction, then another one plus a lot of post-fixing.
- at that point I did a first classification run, but the results were abysmal. Thus I went back to fix the earlier 
  phases.
- 2026-05: Datalab.io delivered a much better text extraction, even keeping tables in Markdown (and HTML, which I meant 
  to publish, but still have to do). From that better data I started improving the summarization prompt, but got stuck 
  manually verifying the results.
- 2026-11: I let Claude improve the prompt and also add some formal quality checks for the summarization (presence of 
  sections, checking that extracted sentences are verbatim). I only manually read less than 5 summaries, but the 
  content also seems ok.
- Finally recreated "summaries.md" with this better prompt (and also a cheaper LLM). 
  (Apparently this was also the first full summarization run since introducing Datalab Markdown files. So yeah, when I 
  have some spare time, it would be worth actually reading some of the summaries.)
- Switch classification to Haiku and much more elaborate prompting. Answer categories are given and even an eval 
  mechanism that can read hand-labeled classifications.
- Haiku 5.5 on Bedrock didn't work. Haiku 4.5 is more expensive (and probably less smart) and needs API tweaks, so I didn't try it. Sonnet 5 also didn't work. Qwen and another open-weights model are unclear about their thinking mode abilities, so I didn't try them. 
- It works with Nova 2 Lite... but we'll see about the quality...

