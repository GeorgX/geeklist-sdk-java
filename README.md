Java Library for the geekli.st API
==================================

This repository contains the Java Client Library for geekli.st. Please consult
http://geekli.st for more information regarding GeekList. Complete API
documentation is available at http://hackers.geekli.st

Usage
-----

	client = new GeeklistApi(CONSUMER_KEY, CONSUMER_SECRET, true);

or like this should you already hold an access token and secret.

	client = new GeeklistApi(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET, true);