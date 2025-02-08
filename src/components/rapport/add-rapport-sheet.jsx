
import { useState, useEffect } from "react"
import { Button } from "@/components/ui/button"
import {
  Sheet,
  SheetContent,
  SheetDescription,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "@/components/ui/sheet"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { ClipboardPlus } from "lucide-react"
import { Textarea } from "@/components/ui/textarea"
import axios from "axios"

export function AddRapportSheet({ onSuccess }) {
  const [open, setOpen] = useState(false)
  const [loading, setLoading] = useState(false)
  const [directorId, setDirectorId] = useState(null)
  const [formData, setFormData] = useState({
    typeRapport: "",
    contenu: "",
    date: "",
  })

  useEffect(() => {
    fetchDirectorId()
  }, [])

  const fetchDirectorId = async () => {
    try {
      const academyName = localStorage.getItem('userName')
      if (academyName) {
        const response = await axios.get(
          `${import.meta.env.VITE_API_URL}/api/directeurs-academie/academie/${academyName}`
        )
        setDirectorId(response.data.id)
      }
    } catch (error) {
      console.error("Error fetching director ID:", error)
    }
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      setLoading(true)
      if (!directorId) {
        throw new Error("Director ID not found")
      }

      await axios.post(
        `${import.meta.env.VITE_API_URL}/api/directeurs-academie/${directorId}/rapports`, 
        {
          typeRapport: formData.typeRapport,
          contenu: formData.contenu,
          date: formData.date,
          generePar: localStorage.getItem('userEmail')
        }
      )
      setOpen(false)
      setFormData({
        typeRapport: "",
        contenu: "",
        date: "",
      })
      if (onSuccess) onSuccess()
    } catch (error) {
      console.error("Error creating rapport:", error)
    } finally {
      setLoading(false)
    }
  }

  const handleChange = (e) => {
    const { id, value } = e.target
    setFormData(prev => ({
      ...prev,
      [id]: value
    }))
  }

  return (
    <Sheet open={open} onOpenChange={setOpen}>
      <SheetTrigger asChild>
        <Button className="gap-2">
          Add Rapport
          <ClipboardPlus className="h-4 w-4" />
        </Button>
      </SheetTrigger>
      <SheetContent className="w-[400px]">
        <SheetHeader>
          <SheetTitle>Add New Rapport</SheetTitle>
          <SheetDescription>
            Fill in the details for the new rapport. Click save when you're done.
          </SheetDescription>
        </SheetHeader>
        <form onSubmit={handleSubmit} className="space-y-4 mt-4">
          <div className="space-y-2">
            <Label htmlFor="typeRapport">Type</Label>
            <Input 
              id="typeRapport"
              value={formData.typeRapport}
              onChange={handleChange}
              placeholder="Rapport formel"
              required
            />
          </div>
          <div className="space-y-2">
            <Label htmlFor="date">Date</Label>
            <Input 
              id="date"
              type="date"
              value={formData.date}
              onChange={handleChange}
              required
            />
          </div>
          <div className="space-y-2">
            <Label htmlFor="contenu">Content</Label>
            <Textarea 
              id="contenu"
              value={formData.contenu}
              onChange={handleChange}
              placeholder="Write your rapport content here..."
              className="min-h-[150px] resize-none"
              required
            />
          </div>
          <div className="flex justify-end space-x-2 pt-4">
            <Button variant="outline" onClick={() => setOpen(false)}>
              Cancel
            </Button>
            <Button type="submit" disabled={loading}>
              {loading ? "Saving..." : "Save"}
            </Button>
          </div>
        </form>
      </SheetContent>
    </Sheet>
  )
}