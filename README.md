# devbox-crate

A pallet crate that creates the basics for a development node.

Installs git, lein, and java.  And can be used to copy a local directory to the
node.

Note: this is a work in progress and is not on clojars yet.

## Usage

Add the following to your `:dependencies`, possibly in your `:pallet` profile if
using `lein pallet up`.

```clj
[com.palletops/devbox-crate "0.1.0-SNAPSHOT"]
```

If you wish to use `lein-pallet-up`, your `pallet.clj` should include something
like:

```clj
(require
 '[pallet.crate.devbox :refer [devbox-from-local]])

(defproject pallet-notifier
  :provider {:vmfest
             {:node-spec
              {:image {:os-family :ubuntu :os-version-matches "12.04"
                       :os-64-bit true}}
              :selectors #{:default}}}

  :groups [(group-spec "pallet-notifier"
             :extends [(devbox-from-local {})])])
```

## License

Copyright © 2013 Hugo Duncan.

Distributed under the Eclipse Public License, the same as Clojure.
