#!/bin/sh

pdflatex documentation
pdflatex documentation
rm -rf *.toc
rm -rf *.log
rm -rf *.out
rm -rf *.aux

