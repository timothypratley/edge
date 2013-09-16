(ns edge.rand)

;; TODO: prevent double selection

(def places ["Alki"
             "Arbor Heights"
             "Atlantic"
             "Ballard"
             "Beacon Hill"
             "Belltown"
             "Broadview Bitter Lake"
             "Broadway"
             "Cedar Park"
             "Columbia City"
             "Downtown"
             "East Queen Anne"
             "Eastlake"
             "Fairmount Park"
             "Fauntleroy"
             "Fremont"
             "Gatewood"
             "Genesee"
             "Georgetown"
             "Green Lake"
             "Haller Lake"
             "Harbor Island"
             "Harrison"
             "High Point"
             "Highland Park"
             "Industrial District"
             "Interbay"
             "International District"
             "Laurelhurst"
             "Leschi"
             "Licton Springs"
             "Lower Queen Anne"
             "Madison Park"
             "Madrona"
             "Magnolia"
             "Mann"
             "Minor"
             "Mountlake"
             "Mt. Baker"
             "North Admiral"
             "North Beach Blue Ridge"
             "North Beacon Hill"
             "North Delridge"
             "North Queen Anne"
             "Northgate"
             "Olympia Hills"
             "Phinney Ridge"
             "Pike Market"
             "Pioneer Square"
             "Portage Bay"
             "Rainier Beach"
             "Ravenna"
             "Riverview"
             "Roxhill"
             "Seaview"
             "Seward Park"
             "South Beacon Hill"
             "South Delridge"
             "South Lake Union"
             "South Park"
             "Stevens"
             "Sunset Hill"
             "University District"
             "Wallingford"
             "Wedgeview Ridge"
             "West Lake"
             "West Queen Anne"
             "Whittier Heights"
             "Yesler Terrace"])

(def tau (* 2 Math/PI))
; PI is wrong http://www.youtube.com/watch?v=jG7vhMMXagQ

(defn rand-loc [dimensions]
  (map rand-int dimensions))

(defn rand-hospital [dimensions]
  {:type :hospital
   :name (str (rand-nth places) " Hospital")
   :location (rand-loc dimensions)})

(defn rand-remote [dimensions]
  {:type :remote
   :name (str (rand-nth places) " Remote")
   :location (rand-loc dimensions)})

(defn rand-drone [dimensions hospitals]
  {:type :drone
   :name (str "D" (rand-int 1000))
   :location (:location (rand-nth hospitals))
   :heading (rand tau)
   :speed 2})

(defn make [n f]
  (vec (map #(assoc %1 :id %2) (repeatedly n f) (range))))

(defn rand-world
  "Generate a random world. Arguments are the desired number of each type."
  [dimensions hospitals remotes drones]
  (let [hs (make hospitals #(rand-hospital dimensions))
        rs (make remotes #(rand-remote dimensions))
        ds (make drones #(rand-drone dimensions hs))]
    {:dimensions dimensions
     :hospitals hs
     :remotes rs
     :drones ds}))

(rand-world [1024 800] 4 25 15)


