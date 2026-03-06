
For downloading the PDFs see `documents/originals/download-all.ps1`
It's done in Powershell by accident; your local AI agent can surely rewrite it for any other environment.

For pdf to text conversion, I tried poppler-utils first (because it easily installs using `apt`), but it's not unpacking ligatures and hyphenation.
Therefore, I switched to pdfminer.six:

I need `pipx`, because the local Python installation is locked.
Luckily, it's just one line more, no big deal.

    sudo apt install pipx
    pipx install pdfminer.six
    for p in originals/*.pdf
    do 
        pdf2txt.py "$p" --outfile "$(basename "$p" .pdf)".txt
    done

Note that all the quotes are needed because some filenames contain spaces.
(At least Bash manages to handle the quotes-in-command-in-quotes correctly.)

Setting up all the permissions for Bedrock is a bit of work in the AWS console. 
If you don't have AWS experience, you might want to use another provider. 

