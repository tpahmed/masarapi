import { AppSidebar } from "@/components/sidebar/app-sidebar"
import { HoverCard, HoverCardContent, HoverCardTrigger } from "@/components/ui/hover-card"
import { SidebarInset, SidebarProvider, SidebarTrigger } from "@/components/ui/sidebar"

export default function Dashboard() {
    return (
        <SidebarProvider>
        <div className="flex min-h-screen dark:bg-background">
          <AppSidebar className="border-r border-gray-300 shadow-[2px_0_10px_rgb(107,114,128)]" />
          <SidebarInset className="flex-1 ">
            <div className="flex flex-col bg-gray-200 h-full">
              <div className="border-b border-zinc-300 dark:border-zinc-700">
                <div className="flex h-16 items-center px-4">
                <HoverCard>
            <HoverCardTrigger>
            <SidebarTrigger className="-ml-1" />
              </HoverCardTrigger>
              <HoverCardContent className="w-12">
                <p className="text-xs">⌘B</p>
              </HoverCardContent>
            </HoverCard>
                </div>
              </div>
              <div className="flex flex-1 flex-col gap-4 p-4 pt-3">
                <div className="grid auto-rows-min gap-4 md:grid-cols-3">
                    <div className="aspect-video rounded-xl bg-muted/50" />
                    <div className="aspect-video rounded-xl bg-muted/50" />
                    <div className="aspect-video rounded-xl bg-muted/50" />
                </div>
                <div className="min-h-[100vh] flex-1 rounded-xl bg-muted/50 md:min-h-min" />
                </div>
              </div>
            </SidebarInset>
              </div>
              </SidebarProvider>
      )
    }
    
