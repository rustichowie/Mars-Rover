(bind ?rand (new java.util.Random))
(bind ?directions (new java.util.ArrayList))
(deftemplate gridbox
    (slot direction)
    (slot obstacle)
    (slot rocks (type INTEGER))
    (slot grain (type INTEGER))
    (slot came_from)
    (slot signal (type INTEGER))
    (slot is_spaceship)
)

(deftemplate rover
    (slot name)
    (slot carrying)
    (slot cluster_found)
)

(deftemplate action
	(slot do)
)


(defrule search
    ?this <- (gridbox (direction this) (signal ?this_signal) (obstacle ?this_obs) (rocks ?this_rocks) (grain ?this_grain) (came_from ?this_came_from) (is_spaceship ?this_is_spaceship))
=>
    (if (and(= ?rover.carrying false)(= ?rover.cluster_found false)) then
        (if (< ?this.rocks 1) then
            (assert (action (do move)))
        else (if (= ?this.rocks 1) then
            (assert (action (do pickup)))
        else
            (assert (action (do cluster_found)))
		(printout t "alert" crlf)
        )
    )
    else (assert(action(do move))))
)

(defrule move_rover
    ?left <- (gridbox (direction left) (signal ?l_signal) (obstacle ?l_obs) (rocks ?l_rocks) (grain ?l_grain) (came_from ?l_came_from) (is_spaceship ?l_is_spaceship))
    ?right <- (gridbox (direction right) (signal ?r_signal) (obstacle ?r_obs) (rocks ?r_rocks) (grain ?r_grain) (came_from ?r_came_from) (is_spaceship ?r_is_spaceship))
    ?top <- (gridbox (direction top) (signal ?t_signal) (obstacle ?t_obs) (rocks ?t_rocks) (grain ?t_grain) (came_from ?t_came_from) (is_spaceship ?t_is_spaceship))
    ?bottom <- (gridbox (direction bottom) (signal ?b_signal) (obstacle ?b_obs) (rocks ?b_rocks) (grain ?b_grain) (came_from ?b_came_from) (is_spaceship ?b_is_spaceship))
    ?this <- (gridbox (direction this) (signal ?this_signal) (obstacle ?this_obs) (rocks ?this_rocks) (grain ?this_grain) (came_from ?this_came_from) (is_spaceship ?this_is_spaceship))

    ?action <- (action (do move))
=>
    (printout t "trying to move" crlf)

    ; Rover is carrying AND At spaceship
    (if (and (= ?rover.carrying true) (= ?this.is_spaceship true)) then
        (assert (action (do drop)))
        (if (= (fetch cluster_found) true) then
            (store alert true)
            (store cluster_found false))
    )

    ; At spaceship AND Rover is cluster_found
    (if (= ?this.is_spaceship true) then
        (modify ?rover (cluster_found false)))
    
    ; Rover NOT carrying AND Rover NOT cluster_found    OR    At spaceship
    (if (or(and(= ?rover.carrying false)(= ?rover.cluster_found false))(and(= ?this.is_spaceship true)(= ?rover.cluster_found true))) then
        (next_direction ?left ?right ?top ?bottom ?this)
    else
        (printout t "going home" crlf)
        (go_home ?left ?right ?top ?bottom ?this)
    )

    ; List of directions bigger than 0
    (if (<= (?directions size) 0) then  
        (go_back ?left ?right ?top ?bottom) 
    else 
        (store direction (?directions get (?rand nextInt (?directions size))))
        (printout t "store direction: ") (printout t (fetch direction) crlf)
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


(defrule cluster
    ?action <- (action (do cluster_found))
=>
    (store cluster_found true)
    (printout t "pickup" crlf)
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
    (if(and(= (check_dir ?left) true)(<= ?left.signal ?this.signal)) then
        (printout t "adding left" crlf)
        (?directions add ?left.direction)
     )   
     (if(and (= (check_dir ?top) true)(<= ?top.signal ?this.signal)) then
        (printout t "adding top" crlf)
        (?directions add ?top.direction))
     (if(and (= (check_dir ?right) true)(<= ?right.signal ?this.signal)) then
        (printout t "adding right" crlf)
        (?directions add ?right.direction))
     (if(and (= (check_dir ?bottom) true)(<= ?bottom.signal ?this.signal)) then
        (printout t "adding bottom" crlf)
        (?directions add ?bottom.direction))
      (if (= (?directions isEmpty) true) then
        (next_direction ?left ?right ?top ?bottom ?this))
      (return nil)
)		

(deffunction next_direction (?left ?right ?top ?bottom ?this)
    (?directions clear)
    (if (= (follow_grain ?left ?right ?top ?bottom ?this) true) then
        (assert(action (do pickup_grain)))
        (printout t "Following grain" crlf)
        (return nil)
    )
    (if(= (check_dir ?left) true) then
        (printout t "adding left" crlf)
        (?directions add ?left.direction)
     )   
     (if(= (check_dir ?top) true) then
        (printout t "adding top" crlf)
        (?directions add ?top.direction))
     (if(= (check_dir ?right) true) then
        (printout t "adding right" crlf)
        (?directions add ?right.direction))
     (if(= (check_dir ?bottom) true) then
        (printout t "adding bottom" crlf)
        (?directions add ?bottom.direction))
      (if (= (?directions isEmpty) true) then
        (printout t ?came_from crlf)
        (?directions add ?came_from))
      (return nil)
)   

(deffunction follow_grain (?left ?right ?top ?bottom ?this) 
    (?directions clear)

    (if(and(> ?left.grain 0)(>= ?left.signal ?this.signal)) then
        (?directions add ?left.direction)
        (return true)
     )   
     (if(and(> ?top.grain 0)(>= ?top.signal ?this.signal)) then
        (printout t "adding top" crlf)
        (?directions add ?top.direction)
        (return true)
    )
     (if(and(> ?right.grain 0)(>= ?right.signal ?this.signal)) then
        (printout t "adding right" crlf)
        (?directions add ?right.direction)
        (return true)
    )
     (if(and(> ?bottom.grain 0)(>= ?bottom.signal ?this.signal)) then
        (printout t "adding bottom" crlf)
        (?directions add ?bottom.direction)
        (return true)
    )
     (if (<= (?directions size) 0) then
        (return false)
     
     )
)

(deffunction go_back (?left ?right ?top ?bottom)
    (foreach ?dir (create$ ?left ?right ?top ?bottom)
        (if (= ?dir.came_from true) then
            (store direction ?dir.direction)
            (break)))
            
)

(deffunction check_dir (?box)
     (if (= ?box.obstacle true) then
        (printout t ?box.direction crlf)
        (printout t "is an obstacle" crlf)
        (return false))
     (if (= ?box.came_from true) then
        (bind ?came_from ?box.came_from)
        (return false))
     (if (and (= ?box.is_spaceship true)(= ?rover.carrying false)) then
        (return false))
     
        (return true)
)


(defrule drop_grain
    ?action <- (action (do drop_grain))
=>
    (store drop_grain true)
    (retract ?action)
)

(defrule pickup_grain
    ?action <- (action (do pickup_grain))
=>
    (store pickup_grain true)
    (retract ?action)
)
