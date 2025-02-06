import { Button } from "@/components/ui/button"
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogFooter,
} from "@/components/ui/dialog"
import { Label } from "@/components/ui/label"
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group"

export function AttendanceDialog({ open, onOpenChange, selectedStudents, onSubmit }) {
  const handleSubmit = (e) => {
    e.preventDefault()
    const formData = new FormData(e.target)
    const status = formData.get("status")
    const date = formData.get("date")
    onSubmit({ status, date, studentIds: selectedStudents })
    onOpenChange(false)
  }

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Mark Attendance</DialogTitle>
        </DialogHeader>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="space-y-2">
            <Label>Date</Label>
            <input
              type="date"
              name="date"
              required
              className="w-full rounded-md border border-gray-300 px-3 py-2"
            />
          </div>
          <div className="space-y-2">
            <Label>Status</Label>
            <RadioGroup name="status" required defaultValue="present" className="flex gap-4">
              <div className="flex items-center space-x-2">
                <RadioGroupItem value="present" id="present" />
                <Label htmlFor="present">Present</Label>
              </div>
              <div className="flex items-center space-x-2">
                <RadioGroupItem value="absent" id="absent" />
                <Label htmlFor="absent">Absent</Label>
              </div>
            </RadioGroup>
          </div>
          <DialogFooter>
            <Button type="submit">Submit</Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  )
}