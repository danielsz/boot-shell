(ns danielsz.boot-shell
  {:boot/export-tasks true}
  (:require [me.raynes.conch :refer [programs with-programs let-programs]]))

(deftask shell
  "Runs a shell script, optionally as root"
  [s script VAL str "the shell script to run"
   p password VAL str "the root password"]
  (with-pre-wrap fileset
    (let [out-files (core/output-files fileset)
          script  (core/by-name [script] out-files)]
      (with-programs [echo sudo sh]
        (if password
          (sudo "-S" "sh" script {:in (echo password)})
          (sh script))))
    fileset))
