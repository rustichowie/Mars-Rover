(bind ?rand (new java.util.Random))
(bind ?directions (new java.util.ArrayList))
(deftemplate gridbox
    (slot direction)
    (slot obstacle)
    (slot rocks (type INTEGER))
    (slot grain (type INTEGER))
    (slot came_from)
    (slot signal)
    (slot is_spaceship)
)

(deftemplate rover
    (slot name)
    (slot carrying)
)

(deftemplate action
	(slot do)
)


(defrule search
?this <- (gridbox (direction this) (signal ?this_signal) (obstacle ?this_obs) (rocks ?this_rocks) (grain ?this_grain) (came_from ?this_came_from) (is_spaceship ?this_is_spaceship))
=>
(if (< ?this.rocks 1) then
        (assert (action (do move)))
     else (if (= ?this.rocks 1) then
        (assert (action (do pickup)))
     else
        (assert (action (do alert)))
		(printout t "alert" crlf)
)))

(defrule move_rover
?left <- (gridbox (direction left) (signal ?l_signal) (obstacle ?l_obs) (rocks ?l_rocks) (grain ?l_grain) (came_from ?l_came_from) (is_spaceship ?l_is_spaceship))
?right <- (gridbox (direction right) (signal ?r_signal) (obstacle ?r_obs) (rocks ?r_rocks) (grain ?r_grain) (came_from ?r_came_from) (is_spaceship ?r_is_spaceship))
?top <- (gridbox (direction top) (signal ?t_signal) (obstacle ?t_obs) (rocks ?t_rocks) (grain ?t_grain) (came_from ?t_came_from) (is_spaceship ?t_is_spaceship))
?bottom <- (gridbox (direction bottom) (signal ?b_signal) (obstacle ?b_obs) (rocks ?b_rocks) (grain ?b_grain) (came_from ?b_came_from) (is_spaceship ?b_is_spaceship))
?this <- (gridbox (direction this) (signal ?this_signal) (obstacle ?this_obs) (rocks ?this_rocks) (grain ?this_grain) (came_from ?this_came_from) (is_spaceship ?this_is_spaceship))

?action <- (action (do move))
=>
(printout t "trying to move" crlf)
(if (and (= ?rover.carrying true) (= ?this.is_spaceship true)) then
 (assert (action (do drop)))
)
(if (= ?rover.carrying false) then
(next_direction ?left ?right ?top ?bottom)

(if (> (?directions size) 0) then
(store direction (?directions get (?rand nextInt (?directions size)))))

else
(go_home ?left ?right ?top ?bottom ?this)
(store direction (?directions get (?rand nextInt (?directions size))))
)

(retract ?left)
(retract ?right)
(retract ?top)
(retract ?bottom)
(retract ?this)
(retract ?action)
(facts)
)



(defrule pickup_rock
?action <- (action (do pickup))
=>
(store pickup true)
(modify ?rover (carrying true))
(assert (action (do move)))
(retract ?action)
)
(defrule alert_rover
?action <- (action (do alert))
=>
(store alert true)
(printout t "prickup" crlf)
(assert (action (do pickup)))
(retract ?action)
)
(defrule drop_rock
?action  <- (action(do drop))
=>
(store drop true)
(modify ?rover (carrying false))
(retract ?action)
)

(deffunction go_home(?left ?right ?top ?bottom ?this)
    (?directions clear)
    (if(and(= (check_dir ?left) true)(< ?left.signal ?this.signal)) then
        (?directions add ?left.direction)
     )   
     (if(and (= (check_dir ?top) true)(< ?top.signal ?this.signal)) then
        (?directions add ?top.direction))
     (if(and (= (check_dir ?right) true)(< ?right.signal ?this.signal)) then
        (?directions add ?right.direction))
     (if(and (= (check_dir ?bottom) true)(< ?bottom.signal ?this.signal)) then
        (?directions add ?bottom.direction))
      (if (= (?directions isEmpty) true) then
        (next_direction ?left ?right ?top ?bottom))
      (return nil)
)		

(deffunction next_direction (?left ?right ?top ?bottom)
    
    (?directions clear)
    (if(= (check_dir ?left) true) then
        (?directions add ?left.direction)
     )   
     (if(= (check_dir ?top) true) then
        (?directions add ?top.direction))
     (if(= (check_dir ?right) true) then
        (?directions add ?right.direction))
     (if(= (check_dir ?bottom) true) then
        (?directions add ?bottom.direction))
      (if (= (?directions isEmpty) true) then
        (?directions add ?came_from))
      (return nil)
)   

(deffunction check_dir (?box)
    (if (= ?box.obstacle true) then
        (return false)
    else (if (= ?box.came_from true) then
        (bind ?came_from ?box.came_from)
        (return false)
    else (if (and (= ?box.is_spaceship true)(= ?rover.carrying false)) then
        (return false)
    else 
        (return true)
))))

(deffunction better_signal (?this ?next)
    (if(> ?this.signal ?next.signal) then
        (return true)
     else 
        (return false)))