indexing
	description: "Objects that ..."
	author: ""
	date: "$Date: 2004/05/25 12:32:08 $"
	revision: "$Revision: 1.1 $"

class
	REPLAY_THREAD

inherit
	THREAD -- CAPTURE ADDON
	rename
			execute as execute_replay
	end

--	CAPTURE_REPLAY_HANDLING


create 
--	make,
	make_reply_thread





feature {NONE} -- Implementation

--	make is do  end

	make_reply_thread (crh : EV_APPLICATION_IMP) is
	do
		ev_app := crh
	ensure
		ev_app /= void
	end

	execute_replay is
	local
		i : INTEGER
	do
		
		from
			i := 0	
		until
			i /= 10
		loop
			io.putstring ("%NJe suis le thread de rejou")
			ev_app.app_implementation.sleep(1000)
			
		end
	end

feature -- attribut
	ev_app : EV_APPLICATION_IMP

invariant
	invariant_clause: True -- Your invariant here

end -- class REPLAY_THREAD
