(deftemplate gridbox
    (slot direction)
    (slot obstacle)
    (slot rocks (type INTEGER))
    (slot grain (type INTEGER))
    (slot came_from)
    (slot signal)
    (slot is_empty)
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
?this <- (gridbox (direction this) (signal ?this_signal) (obstacle ?this_obs) (rocks ?this_rocks) (grain ?this_grain) (came_from ?this_came_from)(is_empty ?this_is_empty) (is_spaceship ?this_is_spaceship))
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
?left <- (gridbox (direction left) (signal ?l_signal) (obstacle ?l_obs) (rocks ?l_rocks) (grain ?l_grain) (came_from ?l_came_from) (is_empty ?l_is_empty) (is_spaceship ?l_is_spaceship))
?right <- (gridbox (direction right) (signal ?r_signal) (obstacle ?r_obs) (rocks ?r_rocks) (grain ?r_grain) (came_from ?r_came_from)(is_empty ?r_is_empty) (is_spaceship ?r_is_spaceship))
?top <- (gridbox (direction top) (signal ?t_signal) (obstacle ?t_obs) (rocks ?t_rocks) (grain ?t_grain) (came_from ?t_came_from)(is_empty ?t_is_empty) (is_spaceship ?t_is_spaceship))
?bottom <- (gridbox (direction bottom) (signal ?b_signal) (obstacle ?b_obs) (rocks ?b_rocks) (grain ?b_grain) (came_from ?b_came_from)(is_empty ?b_is_empty) (is_spaceship ?b_is_spaceship))
?this <- (gridbox (direction this) (signal ?this_signal) (obstacle ?this_obs) (rocks ?this_rocks) (grain ?this_grain) (came_from ?this_came_from)(is_empty ?this_is_empty) (is_spaceship ?this_is_spaceship))

(action (do move))
=>
(if (= ?rover.carrying false) then
(next_direction ?left ?right ?top ?bottom)
(printout t (fetch direction) crlf)
else
(go_home ?left ?right ?top ?bottom ?this))
)
(defrule pickup_rock
(action (do pickup))
=>
(store pickup true)
(printout t "move" crlf)
(assert (action (do move)))
)
(defrule alert_rover
(action (do alert))
=>
(store alert true)
(printout t "prickup" crlf)
(assert (action (do pickup)))
)


(deffunction go_home(?left ?right ?top ?bottom ?this)
    (bind ?dir_ok false)
    (foreach ?dir (create$ ?left ?right ?top ?bottom)
        (if (and(= (check_dir ?dir) true)(= (better_signal ?this ?dir) true)) then
			(store direction ?dir.direction)
			(bind ?dir_ok true)
			(break)))
			
	(if (= ?dir_ok false) then
		(store direction ?came_from))
)		
			


(deffunction next_direction (?left ?right ?top ?bottom)
    (if(= (check_dir ?left) true) then
        (store direction ?left.direction)
     else (if(= (check_dir ?top) true) then
        (store direction ?top.direction)
     else (if(= (check_dir ?right) true) then
        (store direction ?right.direction)
     else (if(= (check_dir ?bottom) true) then
        (store direction ?bottom.direction)
     else 
        (store direction ?came_from)
     ))))
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
    (if(< ?this.signal ?next_signal) then
        (return true)
     else 
        (return false)))
(assert (gridbox (direction this) (signal 5) (obstacle false) (rocks 0)
(grain 0) (came_from false) (is_empty false) (is_spaceship false)))

(bind ?rover (assert (rover (name Lasse) (carrying false)))) 
(assert (gridbox (direction left) (signal 5) (obstacle true) (rocks 0)
	(grain 0) (came_from false) (is_empty false) (is_spaceship false)))
(assert (gridbox (direction right) (signal 6) (obstacle false) (rocks 0)
	(grain 0) (came_from true) (is_empty false) (is_spaceship false)))
(assert (gridbox (direction top) (signal 4) (obstacle false) (rocks 0)
	(grain 0) (came_from false) (is_empty false) (is_spaceship false)))
(assert (gridbox (direction bottom) (signal 7) (obstacle false) (rocks 5)
	(grain 0) (came_from false) (is_empty false) (is_spaceship false)))
(run)
(clear)	