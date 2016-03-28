(ns cards-clojure.core
  (:gen-class))

(def suits [:clubs :spades :hearts :diamonds])
(def ranks (range 1 14))
(def rank-names {1 :ace, 11 :jack, 12 :queen, 13 :king})

(def test-flush #{{:suit :clubs :rank 5}
                  {:suit :clubs :rank 8}
                  {:suit :clubs :rank 2}
                  {:suit :clubs :rank 11}})

(def test-straight-flush #{{:suit :spades :rank 5}
                           {:suit :spades :rank 6}
                           {:suit :spades :rank 7}
                           {:suit :spades :rank 8}})

(def test-straight #{{:suit :clubs :rank 5}
                     {:suit :spades :rank 6}
                     {:suit :hearts :rank 7}
                     {:suit :diamonds :rank 8}})

(defn create-deck [] 
  (set
    (for [suit suits
          rank ranks]
      {:suit suit
       :rank rank})))
      
(defn create-hands [deck]
  (set
    (for [c1 deck
          c2 (disj deck c1)
          c3 (disj deck c1 c2)
          c4 (disj deck c1 c2 c3)]
      #{c1 c2 c3 c4})))

(defn flush? [hand]
  (= 1 (count (set (map :suit hand)))))

(defn straight-flush? [hand]
  (and
    (= 1 (count (set (map :suit hand))))
    (and (= 3 (- (apply max (map :rank hand))
                 (apply min (map :rank hand))))
         (= 4 (count (set (map :rank hand)))))))
  
(defn straight? [hand]
  (and (= 3 (- (apply max (map :rank hand))
               (apply min (map :rank hand))))
       (= 4 (count (set (map :rank hand))))))   
    
(defn four-of-a-kind? [hand]
  (= 1 (count (set (map :rank hand)))))
  
(defn three-of-a-kind? [hand]
  (and (= 3 (apply max (map val (frequencies (map :rank hand)))))
       (= 1 (apply min (map val (frequencies (map :rank hand)))))))
  
(defn two-pair? [hand]
  (and (= 2 (apply max (map val (frequencies (map :rank hand)))))
       (= 2 (apply min (map val (frequencies (map :rank hand)))))))
  
(defn -main []
  (time
    (let [deck (create-deck)
          hands (create-hands deck)
          flush-hands (filter flush? hands)
          straight-flush-hands (filter straight-flush? hands)
          straight-hands (filter straight? hands)
          foak-hands (filter four-of-a-kind? hands)
          toak-hands (filter three-of-a-kind? hands)
          two-pair-hands (filter two-pair? hands)]
      (count straight-flush-hands))))
