(ns edge.plan-test
  (:require [clojure.test :refer :all]
            [edge.plan :as plan]
            [edge.world :as world]
            [edge.rand :as rand]
            [edge.commands :as commands]
            [edge.event-sourcing :as event-sourcing]))

(deftest about-planning
  (let [w (atom (rand-world [1024 800] 4 25 15))]
    (rand-mission @w)
    (accept @w {:event :mission-created :mission (rand-mission @w)})
    (dotimes [i 10]
      (swap! w accept {:event :mission-created :mission (rand-mission @w)}))
    (println ((plan @w #(weighter @w %1 %2)) :drones))
    (update-drones)))

;;TODO: does not return an answer... is it just too large?
[[610.0836008286077 0.0 278.94981627525766 357.19182521440774 625.9360989749673 0.0 509.1659847240387 601.0199663904685 0.0 0.0 601.0199663904685 0.0 556.1600489067872 0.0 0.0 299.2072860075436 0.0 509.1659847240387 0.0 0.0 0.0 0.0 847.1699947472172 0.0 601.187158878165 750.1466523287296 0.0 0.0 541.9704789008346 181.8598361376145] [373.7084425056517 282.4535360019414 257.55970181687974 74.95331880577403 404.01980149492675 236.02754076590298 229.804264538324 411.17514516322603 236.02754076590298 236.02754076590298 411.17514516322603 236.02754076590298 293.4655005277452 282.4535360019414 282.4535360019414 67.26812023536856 236.02754076590298 229.804264538324 236.02754076590298 672.7295147382788 236.02754076590298 236.02754076590298 611.0883733143677 282.4535360019414 432.4742766916895 552.4527129085349 282.4535360019414 236.02754076590298 283.5207223467096 288.39036044916617] [458.3503027161649 560.6424885789517 643.137621353315 457.0918944807488 307.18886698576824 303.7103883636515 301.23910768690047 582.7177704515283 303.7103883636515 303.7103883636515 582.7177704515283 560.6424885789517 380.5798207997897 807.7406762074075 807.7406762074075 542.379940632026 560.6424885789517 301.23910768690047 303.7103883636515 303.7103883636515 303.7103883636515 560.6424885789517 266.9569253643741 560.6424885789517 632.4626471183891 381.0577384071868 807.7406762074075 303.7103883636515 279.60865508778517 746.00268096033] [56.000000000000206 110.506878003518 56.000000000000206 56.000000000000206 56.000000000000206 563.8964563006039 56.000000000000206 56.000000000000206 861.5955310545152 861.5955310545152 56.000000000000206 338.0853418156953 56.000000000000206 109.59287424282464 206.68259409805134 56.000000000000206 354.976246259026 56.000000000000206 723.5015387189242 893.7198625441703 861.5955310545152 412.6943791901355 56.000000000000206 632.2181432997671 56.000000000000206 56.000000000000206 118.4626968693623 635.3421367381991 56.000000000000206 56.000000000000206] [311.75631509241316 509.1659847240387 387.88271423202144 155.8717421471897 258.9710408520613 318.2341904949875 0.0 411.0048661512416 480.6506007486103 480.6506007486103 411.0048661512416 318.2341904949875 199.80990966416056 509.1659847240387 509.1659847240387 244.7549795203358 318.2341904949875 0.0 318.2341904949875 480.6506007486103 480.6506007486103 318.2341904949875 420.3772115612358 318.2341904949875 452.67648492052246 413.726963104896 509.1659847240387 318.2341904949875 106.78014796768171 470.67292252688594] [579.0379952990995 0.0 84.86459803710851 252.5173261382276 308.4234102658227 0.0 318.2341904949875 638.003134788537 0.0 0.0 638.003134788537 0.0 478.44017389847187 0.0 0.0 303.2886413962778 0.0 318.2341904949875 0.0 0.0 0.0 0.0 529.0538724931516 0.0 663.9826804970141 414.60945478847924 0.0 0.0 283.28960446864266 202.48456731316588] [680.0360284573163 529.0538724931516 611.8594609875703 551.457160620841 222.27235545609355 61.66036003787198 420.3772115612358 799.2759223196955 61.66036003787198 61.66036003787198 799.2759223196955 61.66036003787198 580.9104922447175 529.0538724931516 529.0538724931516 649.333504448985 61.66036003787198 420.3772115612358 61.66036003787198 61.66036003787198 61.66036003787198 61.66036003787198 0.0 61.66036003787198 846.641010109952 165.38742394752995 529.0538724931516 61.66036003787198 330.94561486745823 731.5312706918277] [59.289604468646075 443.61017651198335 59.289604468646075 59.289604468646075 59.289604468646075 263.02671231105495 59.289604468646075 59.289604468646075 501.61505295259855 501.61505295259855 59.289604468646075 218.15671581504793 59.289604468646075 507.14881229860805 495.18742438024793 59.289604468646075 284.02728477673054 59.289604468646075 445.50206800909393 480.58278126425415 501.61505295259855 217.5524900766673 59.289604468646075 691.9230240825267 59.289604468646075 59.289604468646075 516.1179084198229 236.8230993775351 59.289604468646075 59.289604468646075] [123.2072860075448 272.0278043151047 123.2072860075448 123.2072860075448 123.2072860075448 394.3087964058633 123.2072860075448 123.2072860075448 695.149520862891 695.149520862891 123.2072860075448 164.6422994203065 123.2072860075448 288.45405512438674 189.86948737214905 123.2072860075448 175.9999999999988 123.2072860075448 546.4019533043037 757.6938581626633 695.149520862891 239.4216158870129 123.2072860075448 504.00885023147197 123.2072860075448 123.2072860075448 297.191744099853 541.9473705189481 123.2072860075448 123.2072860075448] [199.99999999999943 802.6502421305574 199.99999999999943 199.99999999999943 199.99999999999943 284.08618047625777 199.99999999999943 199.99999999999943 216.50651056829196 216.50651056829196 199.99999999999943 473.6418789136813 199.99999999999943 863.0268892569204 798.1420444994939 199.99999999999943 510.88677016807736 199.99999999999943 334.0500137234816 138.48097816169553 216.50651056829196 413.05585892308585 199.99999999999943 821.7175715613752 199.99999999999943 199.99999999999943 872.1317066017185 257.85146316310687 199.99999999999943 199.99999999999943] [148.49458277265322 150.44264475333176 148.49458277265322 148.49458277265322 148.49458277265322 498.4155359590533 148.49458277265322 148.49458277265322 779.6428879038124 779.6428879038124 148.49458277265322 302.95293082086283 148.49458277265322 221.81572351573922 334.0700113855474 148.49458277265322 347.6243200914078 148.49458277265322 675.3764339040449 772.8182520281571 779.6428879038124 367.4889210825859 148.49458277265322 706.4813870905049 148.49458277265322 148.49458277265322 230.16819967123706 492.240687324409 148.49458277265322 148.49458277265322] [243.99999999999937 624.5442022378946 243.99999999999937 243.99999999999937 243.99999999999937 366.99836873197034 243.99999999999937 243.99999999999937 469.4281028188527 469.4281028188527 243.99999999999937 428.50623749824115 243.99999999999937 698.4761725431151 716.1694960162063 243.99999999999937 489.70282649133804 243.99999999999937 511.8106818001365 332.94221363532466 469.4281028188527 404.7270362166282 243.99999999999937 881.4103534148146 243.99999999999937 243.99999999999937 706.8927916612153 15.849956705788818 243.99999999999937 243.99999999999937] [579.0379952990995 0.0 84.86459803710851 252.5173261382276 308.4234102658227 0.0 318.2341904949875 638.003134788537 0.0 0.0 638.003134788537 0.0 478.44017389847187 0.0 0.0 303.2886413962778 0.0 318.2341904949875 0.0 0.0 0.0 0.0 529.0538724931516 0.0 663.9826804970141 414.60945478847924 0.0 0.0 283.28960446864266 202.48456731316588] [610.0836008286077 0.0 278.94981627525766 357.19182521440774 625.9360989749673 0.0 509.1659847240387 601.0199663904685 0.0 0.0 601.0199663904685 0.0 556.1600489067872 0.0 0.0 299.2072860075436 0.0 509.1659847240387 0.0 0.0 0.0 0.0 847.1699947472172 0.0 601.187158878165 750.1466523287296 0.0 0.0 541.9704789008346 181.8598361376145] [610.0836008286077 0.0 278.94981627525766 357.19182521440774 625.9360989749673 0.0 509.1659847240387 601.0199663904685 0.0 0.0 601.0199663904685 0.0 556.1600489067872 0.0 0.0 299.2072860075436 0.0 509.1659847240387 0.0 0.0 0.0 0.0 847.1699947472172 0.0 601.187158878165 750.1466523287296 0.0 0.0 541.9704789008346 181.8598361376145] [458.3503027161649 560.6424885789517 643.137621353315 457.0918944807488 307.18886698576824 303.7103883636515 301.23910768690047 582.7177704515283 303.7103883636515 303.7103883636515 582.7177704515283 560.6424885789517 380.5798207997897 807.7406762074075 807.7406762074075 542.379940632026 560.6424885789517 301.23910768690047 303.7103883636515 303.7103883636515 303.7103883636515 560.6424885789517 266.9569253643741 560.6424885789517 632.4626471183891 381.0577384071868 807.7406762074075 303.7103883636515 279.60865508778517 746.00268096033] [8.51732613822789 395.34550874169497 8.51732613822789 8.51732613822789 8.51732613822789 310.8027728269317 8.51732613822789 8.51732613822789 555.5961695923124 555.5961695923124 8.51732613822789 227.90616382787243 8.51732613822789 462.1153868716487 473.6570802691553 8.51732613822789 295.17355192442966 8.51732613822789 494.42581774504487 527.8600823604218 555.5961695923124 243.99999999999972 8.51732613822789 706.3484479306085 8.51732613822789 8.51732613822789 470.93838605525013 262.7726021492423 8.51732613822789 8.51732613822789] [244.00000000000182 861.4633690437307 244.00000000000182 244.00000000000182 244.00000000000182 269.6640302810684 244.00000000000182 244.00000000000182 94.44988691503531 94.44988691503531 244.00000000000182 491.4709005103843 244.00000000000182 913.8503920561699 812.4681178803044 244.00000000000182 513.9865707375757 244.00000000000182 248.18390871705486 195.29728195468292 94.44988691503531 421.26484137934466 244.00000000000182 769.1670499806565 244.00000000000182 244.00000000000182 923.0648031909916 374.12911333199713 244.00000000000182 244.00000000000182] [610.0836008286077 0.0 278.94981627525766 357.19182521440774 625.9360989749673 0.0 509.1659847240387 601.0199663904685 0.0 0.0 601.0199663904685 0.0 556.1600489067872 0.0 0.0 299.2072860075436 0.0 509.1659847240387 0.0 0.0 0.0 0.0 847.1699947472172 0.0 601.187158878165 750.1466523287296 0.0 0.0 541.9704789008346 181.8598361376145] [610.0836008286077 0.0 278.94981627525766 357.19182521440774 625.9360989749673 0.0 509.1659847240387 601.0199663904685 0.0 0.0 601.0199663904685 0.0 556.1600489067872 0.0 0.0 299.2072860075436 0.0 509.1659847240387 0.0 0.0 0.0 0.0 847.1699947472172 0.0 601.187158878165 750.1466523287296 0.0 0.0 541.9704789008346 181.8598361376145]]