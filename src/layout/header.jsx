import { UserNav } from "@/components/dashboard/user-nav"
import {   SidebarTrigger } from "@/components/ui/sidebar"
import { HoverCard, HoverCardTrigger, HoverCardContent } from "@/components/ui/hover-card"
import { SearchCommand } from "@/components/dashboard/SearchDialog"
export default function Header() {

    return (
        <header className="border-b border-zinc-300 dark:border-zinc-700">
        <div className="flex items-center h-16 px-4 gap-4">
          <div className="flex-1 flex items-center gap-2 bg-muted/40 rounded-md px-2">
          <HoverCard>
            <HoverCardTrigger>
            <SidebarTrigger className="-ml-1" />
              </HoverCardTrigger>
              <HoverCardContent className="w-12">
                <p className="text-xs">⌘B</p>
              </HoverCardContent>
            </HoverCard>
            <SearchCommand/>
          </div>
          <UserNav />

        </div>
      </header>
    )
}