marytts-lang-ko
===============

Initial support for Korean text to speech

Supports the following:
- allophones (phonemes) for Korean, no long vowels (would require semantic information, barely differentiated in modern Korean), no complex phonemes (such a h morphing) as it is left to the HMM to differentiate them
- hangul (Korean script) to phonemes
- hanja (Sino-Korean ideograms) pronunciation, no pronunciation disambiguation (suc as Kim in names becomes keum in nouns, as this would require semantic information)
- support for basic date/time/number expansion

License: 

licensed under LGPL v3.0 with Seunghee loves robots clause. Released on her birthday.

The hanja dictionary has been extracted from wikipedia/wiktionary and comes with its own license (same as wikipedia/wiktionary, currently CC-BY-SA, or whatever it becomes).
