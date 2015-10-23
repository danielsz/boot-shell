(set-env!
 :source-paths #{"src"}
 :dependencies '[[adzerk/bootlaces "0.1.12" :scope "test"]
                 [me.raynes/conch "0.8.0"]])

(require '[adzerk.bootlaces :refer :all])

(def +version+ "0.0.1")
(bootlaces! +version+)

(task-options!
 aot {:namespace '#{danielsz.boot-shell}}
 pom {:project 'danielsz/boot-shell
      :version +version+
      :scm {:name "git"
            :url "https://github.com/danielsz/boot-shell"}})

(deftask dev
  []
  (comp
   (repl :server true)
   (wait)))
