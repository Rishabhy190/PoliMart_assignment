import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MediaService {
  private apiUrl = 'http://localhost:8081/api/media';

  constructor(private http: HttpClient) { }

  upload(file: File, description: string): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('description', description);

    return this.http.post(`${this.apiUrl}/upload`, formData);
  }

  list(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/list`);
  }

  download(mediaId: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/download/${mediaId}`, {
      responseType: 'blob'
    });
  }
} 